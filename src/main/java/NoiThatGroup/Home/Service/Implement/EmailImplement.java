package NoiThatGroup.Home.Service.Implement;

import NoiThatGroup.Home.Dto.request.EmailRequest;
import NoiThatGroup.Home.Dto.request.PasswordRequest;
import NoiThatGroup.Home.Dto.request.TokenRequest;
import NoiThatGroup.Home.Enity.Account;
import NoiThatGroup.Home.Enity.EmailSender;
import NoiThatGroup.Home.Enity.User;
import NoiThatGroup.Home.Enums.ErrorCode;
import NoiThatGroup.Home.Exception.AppException;
import NoiThatGroup.Home.Repository.AccountRepository;
import NoiThatGroup.Home.Repository.EmailRepository;
import NoiThatGroup.Home.Repository.InvalidatedTokenRepository;
import NoiThatGroup.Home.Repository.UserRepository;
import NoiThatGroup.Home.Service.EmailService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AllArgsConstructor;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.*;

@Service
@AllArgsConstructor
public class EmailImplement implements EmailService {

    EmailRepository emailRepository;

    InvalidatedTokenRepository invalidatedTokenRepository;

    AccountRepository accountRepository;

    UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @NonFinal
    protected static final String SIGNER_KEY="630F20D84D4187F778E537CD0AE9582D0DB5DA98057668461651A928F3E3A0CF6C1E205D3A8B7E24BB767357DFAF39C264EA";

    @Override
    public boolean sendMail(TokenRequest tokenRequest) throws JOSEException, ParseException {
        if(!verifyToken(tokenRequest.getToken())){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        SignedJWT signedJWT = SignedJWT.parse(tokenRequest.getToken());
        var username = signedJWT.getJWTClaimsSet().getSubject();
        var account = accountRepository.findAccountByUsername(username);
        if(account == null) throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        var emailSend = emailRepository.findById(account.getId());
        if(!emailSend.isEmpty()){
            emailRepository.delete(emailSend.get());
        }
        User email = userRepository.findUserByAccountId(account.getId());
        if (email == null) throw new AppException(ErrorCode.EMAIL_NOT_FOUND);

        var token = generalTokenEmail(account.getUsername());

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("vietyts2003@gmail.com");
        simpleMailMessage.setTo(email.getEmail());
        simpleMailMessage.setSubject("Verify Account NoiThatShop");
        simpleMailMessage.setText("Please click on the following link to verify your email.\n" +
                "Link is only valid for 5 minutes \n" +
                "http://localhost:8080/auth/verify?code="+token);
        EmailSender emailSender = EmailSender.builder()
                .id(account.getId())
                .date(new Date())
                .token(token)
                .build();
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        Runnable deletedEmail = () -> {
            emailRepository.delete(emailSender);
        };
        scheduledExecutorService.schedule(deletedEmail,5,TimeUnit.MINUTES);
        scheduledExecutorService.shutdown();
        emailRepository.save(emailSender);
        javaMailSender.send(simpleMailMessage);
        return true;
    }

    @Override
    public boolean confirmEmail(TokenRequest tokenRequest) throws ParseException, JOSEException {
        var verify = verifyToken(tokenRequest.getToken());
        if(!verify) throw new AppException(ErrorCode.UNAUTHORIZED);
        SignedJWT signedJWT = SignedJWT.parse(tokenRequest.getToken());
        JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
        var object = jwtClaimsSet.getClaim("active");
        if (object == null) throw new AppException(ErrorCode.UNAUTHORIZED);
        var account = accountRepository.findAccountByUsername(signedJWT.getJWTClaimsSet().getSubject());
        Optional<EmailSender> emailSender = emailRepository.findById(account.getId());
        if(emailSender.isEmpty()) throw new AppException(ErrorCode.CODE_EXPIRED);
        if (!tokenRequest.getToken().equals(emailSender.get().getToken())) throw new AppException(ErrorCode.UNAUTHORIZED);
        account.setActive(true);
        accountRepository.save(account);
        emailRepository.delete(emailSender.get());
        return true;

    }

    @Override
    public boolean sendMailPassword(EmailRequest emailRequest) throws ParseException, JOSEException {
        if (!userRepository.existsByEmail(emailRequest.getEmail())) throw new AppException(ErrorCode.EMAIL_NOT_FOUND);
        User user = userRepository.findUserByEmail(emailRequest.getEmail());
        if (user == null) throw new AppException(ErrorCode.EMAIL_NOT_FOUND);
        Account account = accountRepository.findAccountById(user.getAccount().getId());

        if(account == null) throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);
        var emailSend = emailRepository.findById(account.getId());
        if(!emailSend.isEmpty()){
            emailRepository.delete(emailSend.get());
        }

        var token = generalTokenEmailPassword(account.getUsername());

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("vietyts2003@gmail.com");
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("Change Password NoiThatShop");
        simpleMailMessage.setText("We received a request to change your password, please click on the link below to change your password\n" +
                "If this is not you, please ignore this email \n" +
                "The link will be valid for 5 minutes \n" +
                "http://localhost:8080/auth/change?code="+token);
        EmailSender emailSender = EmailSender.builder()
                .id(account.getId())
                .date(new Date())
                .token(token)
                .build();
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        Runnable deletedEmail = () -> {
            emailRepository.delete(emailSender);
        };
        scheduledExecutorService.schedule(deletedEmail,5,TimeUnit.MINUTES);
        scheduledExecutorService.shutdown();

        emailRepository.save(emailSender);
        javaMailSender.send(simpleMailMessage);
        return true;
    }

    @Override
    public boolean confirmMailPassword(TokenRequest tokenRequest) throws ParseException, JOSEException {
        var verify = verifyToken(tokenRequest.getToken());
        if(!verify) throw new AppException(ErrorCode.UNAUTHORIZED);
        SignedJWT signedJWT = SignedJWT.parse(tokenRequest.getToken());
        JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
        var object = jwtClaimsSet.getClaim("forgot");
        if (object == null) throw new AppException(ErrorCode.UNAUTHORIZED);
        var account = accountRepository.findAccountByUsername(signedJWT.getJWTClaimsSet().getSubject());
        Optional<EmailSender> emailSender = emailRepository.findById(account.getId());
        if(emailSender.isEmpty()) throw new AppException(ErrorCode.CODE_EXPIRED);
        if (!tokenRequest.getToken().equals(emailSender.get().getToken())) throw new AppException(ErrorCode.UNAUTHORIZED);


        emailRepository.delete(emailSender.get());
        return true;
    }

    @Override
    public boolean saveAccount(PasswordRequest passwordRequest) throws ParseException, JOSEException {
        if(!confirmMailPassword(new TokenRequest(passwordRequest.getToken()))) throw new AppException(ErrorCode.UNAUTHORIZED);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        if(!passwordRequest.getPassword().equals(passwordRequest.getRepassword()))
            throw new AppException(ErrorCode.PASSWORD_NOT_MATCH);

        SignedJWT signedJWT = SignedJWT.parse(passwordRequest.getToken());
        var username = signedJWT.getJWTClaimsSet().getSubject();
        var account = accountRepository.findAccountByUsername(username);
        account.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        accountRepository.save(account);
        return true;
    }


    private boolean verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date exprityTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verify = signedJWT.verify(jwsVerifier);
        if(!(verify && exprityTime.after(new Date()))){
            throw new AppException(ErrorCode.UNAUTHORIZED);

        }

        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        return true;

    }

    String generalTokenEmail(String username){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("NoiThat.Com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(5, ChronoUnit.MINUTES).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("active",1)
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader,payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

    }

    String generalTokenEmailPassword(String username){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("NoiThat.Com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(5, ChronoUnit.MINUTES).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("forgot",1)
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader,payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

    }


}
