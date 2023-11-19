package com.autoya.autoya_api.iam.infraestructure.token.jwt.services;

import com.autoya.autoya_api.iam.infraestructure.token.jwt.BearerTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.time.DateUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public abstract class TokenServiceImpl implements BearerTokenService {

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
     * @return String the token
     */
    @Override
    public String generateToken(String username) {
        return buildTokenWithDefaultParameters(username);
    }

    private String buildTokenWithDefaultParameters(String username){
        var issuedAt = new Date();
        var expiration = DateUtils.addDays(issuedAt, expirationDays);
        var key = getSigningKey();
        return Jwts.builder()
                .subject(username)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    private SecretKey getSigningKey() {
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
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }
    /**
     * Validate a token
     *
     * @param token the token
     * @return true if the token is valid, false otherwise
     */
    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            LOGGER.info("Token is valid");
            return true;
        } catch (SignatureException e) {
            LOGGER.error("Invalid JSON WEB TOKEN Signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JSON WEB TOKEN: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("JSON WEB TOKEN is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JSON WEB TOKEN is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JSON WEB TOKEN claims string is empty: {}", e.getMessage());

        }
        return false;
    }

    private boolean isTokenPresentIn(String authorizationParameter) {
        return StringUtils.hasText(authorizationParameter);
    }

    private boolean isBearerTokenIn(String authorizationParameter) {
        return authorizationParameter.startsWith(BEARER_TOKEN_PREFIX);
    }

    private String extractTokenFrom(String authorizationHeaderParameter) {
        return authorizationHeaderParameter.substring(TOKEN_BEGIN_INDEX);
    }

    private String getAuthorizationParameterFrom(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_PARAMETER_NAME);
    }

    @Override
    public String getBearerTokenFrom(HttpServletRequest request) {
        String parameter = getAuthorizationParameterFrom(request);
        if (isTokenPresentIn(parameter) && isBearerTokenIn(parameter)) return extractTokenFrom(parameter);
        return null;
    }

    /**
     * This method is responsible for generating the JWT token from the authentication object
     *
     * @param authentication the authentication object
     * @return Authentication
     */
    @Override
    public String generateToken(Authentication authentication) {
        return buildTokenWithDefaultParameters(authentication.getName());
    }
}
