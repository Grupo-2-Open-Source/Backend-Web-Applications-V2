package com.autoya.autoya_api.iam.application.internal.commandservices;



import com.autoya.autoya_api.iam.domain.model.aggregates.User;
import com.autoya.autoya_api.iam.domain.model.commands.SingInCommands;
import com.autoya.autoya_api.iam.domain.model.commands.SingUpCommands;
import com.autoya.autoya_api.iam.domain.services.UserCommandsService;
import com.autoya.autoya_api.iam.infraestructure.persistence.jpa.repository.RoleRepository;
import com.autoya.autoya_api.iam.infraestructure.persistence.jpa.repository.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandsService{

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public UserCommandServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * @param command
     * @return
     */
    @Override
    public Optional<ImmutablePair<User, String>> handle(SingInCommands command) {
        return Optional.empty();
    }

    /**
     * @param command
     * @return
     */
    @Override
    public Optional<User> handle(SingUpCommands command) {
        return Optional.empty();
    }
}
