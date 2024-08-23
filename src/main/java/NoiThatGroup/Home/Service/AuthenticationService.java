package NoiThatGroup.Home.Service;

import NoiThatGroup.Home.Dto.request.AccountRequest;
import NoiThatGroup.Home.Dto.request.TokenRequest;
import NoiThatGroup.Home.Dto.respone.AuthenticationResponses;
import NoiThatGroup.Home.Dto.respone.IntrospectTokenResponses;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

public interface AuthenticationService {

    AuthenticationResponses authentication(AccountRequest accountRequest);

    IntrospectTokenResponses introspect(TokenRequest tokenRequest) throws ParseException, JOSEException;

    void logout(TokenRequest tokenRequest) throws ParseException, JOSEException;

    AuthenticationResponses refreshToken(TokenRequest tokenRequest) throws ParseException, JOSEException;

    boolean checkActive(String token) throws ParseException;
}
