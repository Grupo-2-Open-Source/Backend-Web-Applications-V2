package com.autoya.autoya_api.iam.domain.services;

import com.autoya.autoya_api.iam.domain.model.aggregates.User;
import com.autoya.autoya_api.iam.domain.model.commands.SingInCommands;
import com.autoya.autoya_api.iam.domain.model.commands.SingUpCommands;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public interface UserCommandsService {
    Optional<ImmutablePair<User,String>> handle(SingInCommands command);

    Optional<User> handle(SingUpCommands command);
}
