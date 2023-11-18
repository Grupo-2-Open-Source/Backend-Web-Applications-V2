package com.autoya.autoya_api.iam.application.internal.outboundservices;

public interface HashingService {
    /**
     * Encode a password
     * @param rawPassword the password
     * @return string the encoded password
     */
    String encode(CharSequence rawPassword);
    /**
     * Matches the raw password with the encoded password
     * @param rawPassword the password
     * @param encodedPassword the encoded password
     * @return true if the password matches the encoded password, false otherwise
     */

    boolean matches(CharSequence rawPassword, String encodedPassword);
}
