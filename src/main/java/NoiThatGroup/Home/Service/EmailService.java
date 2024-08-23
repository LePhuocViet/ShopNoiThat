package NoiThatGroup.Home.Service;

import NoiThatGroup.Home.Dto.request.EmailRequest;
import NoiThatGroup.Home.Dto.request.PasswordRequest;
import NoiThatGroup.Home.Dto.request.TokenRequest;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface EmailService {

    boolean sendMail(EmailRequest emailRequest) throws JOSEException, ParseException;

    boolean confirmEmail(EmailRequest emailRequest) throws ParseException, JOSEException;

    boolean sendMailPassword(PasswordRequest passwordRequest) throws ParseException, JOSEException;

    boolean confirmMailPassword(EmailRequest emailRequest) throws ParseException, JOSEException;
}
