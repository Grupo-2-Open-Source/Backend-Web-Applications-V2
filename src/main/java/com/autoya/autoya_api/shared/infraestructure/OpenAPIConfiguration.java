package com.autoya.autoya_api.shared.infraestructure;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for defining OpenAPI documentation settings.
 * This class is annotated with {@link Configuration}, indicating its role as a configuration component.
 */
@Configuration
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI newschoolPlatformOpenApi(){
        return  new OpenAPI()
                .info(new Info().title("Autoya API")
                        .description(
                                "API Autoya Documentation REST API"
                        )
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("https://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Register Plafform Documentation")
                        .url("https://autoya-plarform.github.org/docs"));
    }
}
