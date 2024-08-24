package NoiThatGroup.Home.Service;

import NoiThatGroup.Home.Dto.request.EmailRequest;
import NoiThatGroup.Home.Dto.request.PasswordRequest;
import NoiThatGroup.Home.Dto.request.TokenRequest;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface EmailService {

    boolean sendMail(TokenRequest tokenRequest) throws JOSEException, ParseException;

    boolean confirmEmail(TokenRequest tokenRequest) throws ParseException, JOSEException;

    boolean sendMailPassword(EmailRequest emailRequest) throws ParseException, JOSEException;

    boolean confirmMailPassword(TokenRequest tokenRequest) throws ParseException, JOSEException;

    boolean saveAccount(PasswordRequest passwordRequest) throws ParseException, JOSEException;
}
