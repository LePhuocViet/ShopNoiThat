package NoiThatGroup.Home.Configuration;

import com.nimbusds.jwt.JWT;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.*;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @NonFinal
    protected static final String SIGNER_KEY="630F20D84D4187F778E537CD0AE9582D0DB5DA98057668461651A928F3E3A0CF6C1E205D3A8B7E24BB767357DFAF39C264EA";
    private String[] PUBLIC_ENDPOINT_GET={"/category","/category/find","/items","/items/find"};
    private String[] PUBLIC_ENDPOINT_POST={"/auth/login","/auth/introspect","/auth/logout","/auth/refresh" +
            "/auth/send","/auth/forgot","/users"};
    private String[] USER_ENDPOINT={"/users","/accounts/myinf","/carts"};
    private String[] ADMIN_ENDPOINT={"/accounts","/accounts/search","/accounts/active","/roles","/category","/items"};

    @Autowired
    CustomJwtDecoder customJwtDecoder;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request ->
            request
                    .requestMatchers(HttpMethod.GET,PUBLIC_ENDPOINT_GET).permitAll()
                    .requestMatchers(HttpMethod.POST,PUBLIC_ENDPOINT_POST).permitAll()
                    .requestMatchers(ADMIN_ENDPOINT).hasRole("ADMIN")
                    .requestMatchers(USER_ENDPOINT).hasRole("USER")
                    .anyRequest().authenticated());

        httpSecurity.oauth2ResourceServer(oauth2 ->
            oauth2.jwt(jwtConfigurer ->
                jwtConfigurer.decoder(customJwtDecoder)
                        .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                    .authenticationEntryPoint(new JwtAuthEntryPoint()));


        httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());

        return httpSecurity.build();
    }


    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;

    }
}
