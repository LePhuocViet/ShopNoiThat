package NoiThatGroup.Home.Service.Implement;

import NoiThatGroup.Home.Dto.request.AccountRequest;
import NoiThatGroup.Home.Dto.request.TokenRequest;
import NoiThatGroup.Home.Dto.respone.AuthenticationResponses;
import NoiThatGroup.Home.Dto.respone.IntrospectTokenResponses;
import NoiThatGroup.Home.Enity.Account;
import NoiThatGroup.Home.Enity.InvalidatedToken;
import NoiThatGroup.Home.Enums.ErrorCode;
import NoiThatGroup.Home.Exception.AppException;
import NoiThatGroup.Home.Repository.AccountRepository;
import NoiThatGroup.Home.Repository.InvalidatedTokenRepository;
import NoiThatGroup.Home.Service.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AllArgsConstructor;

import lombok.experimental.NonFinal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class AuthenticationImplement implements AuthenticationService {
    AccountRepository accountRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;

    @NonFinal
    protected static final String SIGNER_KEY="630F20D84D4187F778E537CD0AE9582D0DB5DA98057668461651A928F3E3A0CF6C1E205D3A8B7E24BB767357DFAF39C264EA";
    @Override
    public AuthenticationResponses authentication(AccountRequest accountRequest) {

        Account account = accountRepository.findAccountByUsername(accountRequest.getUsername());
        if (account == null) throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(accountRequest.getPassword(),account.getPassword());
        if(!authenticated) throw new AppException(ErrorCode.USERNAME_OR_PASSWORD_WRONG);

        var token = generateToken(account);
        return AuthenticationResponses.builder()
                .token(token)
                .authentication(true)
                .build();
    }


    public void logout(TokenRequest tokenRequest) throws ParseException, JOSEException {
        var Stringtoken = verifyToken(tokenRequest.getToken());

        String jit = Stringtoken.getJWTClaimsSet().getJWTID();
        Date expiryTime = Stringtoken.getJWTClaimsSet().getExpirationTime();
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        Runnable deteledToken = ()->{
            invalidatedTokenRepository.delete(invalidatedToken);
        };
        scheduledExecutorService.schedule(deteledToken,31, TimeUnit.MINUTES);
        scheduledExecutorService.shutdown();

        invalidatedTokenRepository.save(invalidatedToken);


    }
    public IntrospectTokenResponses introspect(TokenRequest tokenRequest) throws ParseException, JOSEException {
        var token = tokenRequest.getToken();
        boolean valid = true;
        try {
            verifyToken(token);
        }catch (AppException e){
            valid = false;
        }

         return IntrospectTokenResponses.builder()
                 .valid(valid)
                 .build();
    }


    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier jwsVerifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date exprityTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(jwsVerifier);
        if(!(verified && exprityTime.after(new Date()))){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        if(invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHORIZED);
        return signedJWT;
    }

    String generateToken(Account account){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(account.getUsername())
                .issuer("NoiThat.Com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(30, ChronoUnit.MINUTES).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope",buildScope(account))
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

    private String buildScope(Account account){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(account.getRoles())){
            account.getRoles().forEach(role -> {
                stringJoiner.add(role.getName());
            });
        }
        return stringJoiner.toString();
    }

    public AuthenticationResponses refreshToken(TokenRequest tokenRequest) throws ParseException, JOSEException {
        var token = verifyToken(tokenRequest.getToken());
        var jit = token.getJWTClaimsSet().getJWTID();
        var time = token.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .expiryTime(time)
                .id(jit)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        Runnable deteledToken = ()->{
            invalidatedTokenRepository.delete(invalidatedToken);
        };
        scheduledExecutorService.schedule(deteledToken,31, TimeUnit.MINUTES);
        scheduledExecutorService.shutdown();

        var username = token.getJWTClaimsSet().getSubject();
        var account = accountRepository.findAccountByUsername(username);
        if (account == null) throw new AppException(ErrorCode.ACCOUNT_NOT_FOUND);

        var tokenGerna = generateToken(account);

        return AuthenticationResponses.builder()
                .token(tokenGerna)
                .authentication(true)
                .build();
    }

    @Override
    public boolean checkActive(String token) throws ParseException {

        SignedJWT signedJWT = SignedJWT.parse(token);
        var account = accountRepository.findAccountByUsername(signedJWT.getJWTClaimsSet().getSubject());
        if(!account.isActive()) throw new AppException(ErrorCode.ACCOUNT_NOT_ACTIVE);
        return true;
    }

}
