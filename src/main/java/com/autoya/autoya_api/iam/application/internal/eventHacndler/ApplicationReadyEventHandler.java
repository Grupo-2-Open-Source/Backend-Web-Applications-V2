package com.autoya.autoya_api.iam.application.internal.eventHacndler;

import com.autoya.autoya_api.iam.domain.model.commands.SeedRolesCommands;
import com.autoya.autoya_api.iam.domain.services.RoleCommandsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ApplicationReadyEventHandler {

    private final RoleCommandsService roleCommandsService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyEventHandler.class);

    public ApplicationReadyEventHandler(RoleCommandsService roleCommandsService) {
        this.roleCommandsService = roleCommandsService;
    }

    @EventListener
    public void en(ApplicationReadyEvent event) {
        var ApplicationName = event.getApplicationContext().getId();
        LOGGER.info("Starting to verify if rules seeding is needed for {} at {}", ApplicationName, currentTimestamp());
        var seedRolesCommands = new SeedRolesCommands();
        roleCommandsService.handle(seedRolesCommands);
        LOGGER.info("Finished to verify if rules seeding is needed for {} at {}", ApplicationName, currentTimestamp());
    }

    private Timestamp currentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }
}
