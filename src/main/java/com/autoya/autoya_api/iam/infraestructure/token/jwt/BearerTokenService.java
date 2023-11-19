package com.autoya.autoya_api.iam.infraestructure.token.jwt;

import com.autoya.autoya_api.iam.application.internal.outboundservices.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

public interface BearerTokenService extends TokenService {
    /**
     * This method is responsible for extracting the bearer token from the HTTP request
     * @param token the HTTP request
     * @return
     */
    String getBearerTokenFrom(HttpServletRequest token);

    /**
     * This method is responsible for generating the JNT token from the authentication object
     * @param authentication the authentication object
     * @return Authentication
     */
    String generateToken(Authentication authentication);

    String generateToken(org.springframework.security.core.Authentication authentication);
}
