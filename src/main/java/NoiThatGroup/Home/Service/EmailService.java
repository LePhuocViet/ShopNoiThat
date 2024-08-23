package NoiThatGroup.Home.Service;

import NoiThatGroup.Home.Dto.request.EmailRequest;
import NoiThatGroup.Home.Dto.request.TokenRequest;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface EmailService {

    void sendMail(EmailRequest emailRequest) throws JOSEException, ParseException;

    void confirmEmail(EmailRequest emailRequest) throws ParseException, JOSEException;
}
