package com.autoya.autoya_api.iam.infraestructure.token.jwt.services;

import com.autoya.autoya_api.iam.infraestructure.token.jwt.BearerTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class TokenServiceImpl implements BearerTokenService {

    private final Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);

    private static final String AUTHORIZATION_PARAMETER_NAME = "Authorization";
    private static final String BEARER_TOKEN_PREFIX = "Bearer";
    private static final int TOKEN_BEGIN_INDEX = 7;

    @Value("Secret")
    private String secret;

    @Value("7")
    private int expirationDays;

    /**
     * Generate a token for a given username
     *
     * @param username the username
     * @return the token
     */
    @Override
    public String generateToken(String username) {
        return buildTokenWithDefaultParameters(username);
    }

    private String buildTokenWithDefaultParameters(String username) {
        var issuedAt = new Date();
        var expiration = DateUtils.addDays(issuedAt, expirationDays);
        var key = getSignInKey();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    /**
     * Extract the username from a token
     *
     * @param token the token
     * @return String the username
     */
    @Override
    public String getUsernameFromToken(String token) {
        return null;
    }

    /**
     * Validate a token
     *
     * @param token the token
     * @return true if the token is valid, false otherwise
     */
    @Override
    public boolean validateToken(String token) {
        return false;
    }

    /**
     * This method is responsible for extracting the bearer token from the HTTP request
     *
     * @param token the HTTP request
     * @return
     */
    @Override
    public String getBearerToken(HttpServletRequest token) {
        return null;
    }

    /**
     * This method is responsible for generating the JNT token from the authentication object
     *
     * @param authentication the authentication object
     * @return Authentication
     */
    @Override
    public String generateToken(Authentication authentication) {
        return null;
    }
}
