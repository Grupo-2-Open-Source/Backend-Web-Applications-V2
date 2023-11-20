package com.autoya.autoya_api.autoya.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for creating and providing a {@link ModelMapper} bean.
 * This class is annotated with {@link Configuration}, indicating its purpose as a configuration component.
 */
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}