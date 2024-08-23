package NoiThatGroup.Home.Configuration;

import NoiThatGroup.Home.Dto.request.TokenRequest;
import NoiThatGroup.Home.Service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;
@Component
public class CustomJwtDecoder implements JwtDecoder {
    @NonFinal
    protected static final String SIGNER_KEY="630F20D84D4187F778E537CD0AE9582D0DB5DA98057668461651A928F3E3A0CF6C1E205D3A8B7E24BB767357DFAF39C264EA";
    @Autowired
    AuthenticationService authenticationService;

    NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {

        try {
            var introspect = authenticationService.introspect(
                    TokenRequest.builder().token(token).build());
            if (!introspect.isValid()){
                throw new JwtException("Token invalid");
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

        try {
            if (!authenticationService.checkActive(token)){
                throw new JwtException("Account Not Active");
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        if(Objects.isNull(nimbusJwtDecoder)){
            SecretKeySpec secretKeySpec = new SecretKeySpec(SIGNER_KEY.getBytes(),"HS512");

            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec).macAlgorithm(MacAlgorithm.HS512).build();
        }

        return nimbusJwtDecoder.decode(token);
    }
}
