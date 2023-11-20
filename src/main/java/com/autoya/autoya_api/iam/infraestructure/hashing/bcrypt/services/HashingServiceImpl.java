package com.autoya.autoya_api.iam.infraestructure.hashing.bcrypt.services;

import com.autoya.autoya_api.iam.infraestructure.hashing.bcrypt.BCryptHashingService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class HashingServiceImpl implements BCryptHashingService {

    private final BCryptPasswordEncoder passwordEncoder;

    public HashingServiceImpl() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Encode a password
     *
     * @param rawPassword the password
     * @return string the encoded password
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * Matches the raw password with the encoded password
     *
     * @param rawPassword     the password
     * @param encodedPassword the encoded password
     * @return true if the password matches the encoded password, false otherwise
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
