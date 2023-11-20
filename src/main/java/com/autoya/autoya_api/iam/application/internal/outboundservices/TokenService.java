package com.autoya.autoya_api.iam.application.internal.outboundservices;

public interface TokenService {
    /**
     * Generate a token for a given username
     * @param username the username
     * @return the token
     */
    String generateToken(String username);

    /**
     * Extract the username from a token
     * @param token the token
     * @return String the username
     */
    String getUsernameFromToken(String token);


    /**
     * Validate a token
     * @param token the token
     * @return true if the token is valid, false otherwise
     */
    boolean validateToken(String token);
}
