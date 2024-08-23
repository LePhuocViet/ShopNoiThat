package NoiThatGroup.Home.Controller;

import NoiThatGroup.Home.Dto.request.AccountRequest;
import NoiThatGroup.Home.Dto.request.EmailRequest;
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
    ApiResponses<String> sendEmail(@RequestBody EmailRequest emailRequest) throws ParseException, JOSEException {
        emailService.sendMail(emailRequest);
        return ApiResponses.<String>builder()
                .result("success")
                .build();

    }

    @GetMapping("/verify")
    ApiResponses<String> sendEmail(@RequestParam("code") String token) throws ParseException, JOSEException {
        emailService.confirmEmail(new EmailRequest(token));
        return ApiResponses.<String>builder()
                .result("success")
                .build();

    }

}
