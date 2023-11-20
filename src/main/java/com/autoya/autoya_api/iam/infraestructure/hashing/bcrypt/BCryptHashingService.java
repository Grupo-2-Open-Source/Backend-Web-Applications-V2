package com.autoya.autoya_api.iam.infraestructure.hashing.bcrypt;

import com.autoya.autoya_api.iam.application.internal.outboundservices.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.autoya.autoya_api.iam.infraestructure.hashing.bcrypt.services.HashingServiceImpl;
import org.springframework.stereotype.Service;

/**
 * This Interface is responsible for hashing the password
 * It extends the {@link HashingService} and {@link PasswordEncoder} interfaces.
 * This interface is used to inject the BCryot hashing service in the
 * {@link HashingServiceImpl} interface
 */

public interface BCryptHashingService extends HashingService, PasswordEncoder {
}
