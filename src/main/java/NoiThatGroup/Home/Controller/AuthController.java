package NoiThatGroup.Home.Controller;

import NoiThatGroup.Home.Dto.request.AccountRequest;
import NoiThatGroup.Home.Dto.request.EmailRequest;
import NoiThatGroup.Home.Dto.request.PasswordRequest;
import NoiThatGroup.Home.Dto.request.TokenRequest;
import NoiThatGroup.Home.Dto.respone.ApiResponses;
import NoiThatGroup.Home.Dto.respone.AuthenticationResponses;
import NoiThatGroup.Home.Dto.respone.IntrospectTokenResponses;
import NoiThatGroup.Home.Service.AuthenticationService;
import NoiThatGroup.Home.Service.EmailService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    EmailService emailService;

    @PostMapping("/login")
    ApiResponses<AuthenticationResponses> login(@RequestBody @Valid AccountRequest accountRequest){
        return ApiResponses.<AuthenticationResponses>builder()
                .result(authenticationService.authentication(accountRequest))
                .build();
    }

    @PostMapping("/introspect")
    ApiResponses<IntrospectTokenResponses> introspect(@RequestBody TokenRequest tokenRequest) throws ParseException, JOSEException {
        return ApiResponses.<IntrospectTokenResponses>builder()
                .result(authenticationService.introspect(tokenRequest))
                .build();
    }

    @PostMapping("/logout")
    ApiResponses<String> logout(@RequestBody  TokenRequest tokenRequest) throws ParseException, JOSEException {
        authenticationService.logout(tokenRequest);

        return ApiResponses.<String>builder()
                .result("success")
                .build();
    }

    @PostMapping("/refresh")
    ApiResponses<AuthenticationResponses> refresh(@RequestBody TokenRequest tokenRequest) throws ParseException, JOSEException {
        return ApiResponses.<AuthenticationResponses>builder()
                .result(authenticationService
                        .refreshToken(tokenRequest))
                .build();
    }

    @PostMapping("/send")
    ApiResponses<Boolean> sendEmail(@RequestBody EmailRequest emailRequest) throws ParseException, JOSEException {
        return ApiResponses.<Boolean>builder()
                .result(emailService.sendMail(emailRequest))
                .build();

    }

    @GetMapping("/verify")
    ApiResponses<Boolean> confirm(@RequestParam("code") String token) throws ParseException, JOSEException {
        System.out.println(1);
        return ApiResponses.<Boolean>builder()
                .result(emailService.confirmEmail(new EmailRequest(token)))
                .build();

    }
    @PostMapping("/forgot")
    ApiResponses<Boolean> sendEmailForgot(@RequestBody PasswordRequest passwordRequest) throws ParseException, JOSEException {

        return ApiResponses.<Boolean>builder()
                .result( emailService.sendMailPassword(passwordRequest))
                .build();

    }

    @GetMapping("/change")
    ApiResponses<Boolean> confirmMailPassword(@RequestParam("code") String token ) throws ParseException, JOSEException {

        return ApiResponses.<Boolean>builder()
                .result( emailService.confirmMailPassword(new EmailRequest(token)))
                .build();

    }

}
