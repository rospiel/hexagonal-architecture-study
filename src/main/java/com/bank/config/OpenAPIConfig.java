package com.bank.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Info info = new Info()
                .title("API BANK SLIP")
                .description("API BANK SLIP TO STUDY HEXAGONAL ARCHITECTURE")
                .version("v1");

        return new OpenAPI().info(info)
                .tags(createTags());
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            openApi.getPaths()
                    .values()
                    .stream()
                    .flatMap(pathItem -> pathItem.readOperations().stream())
                    .forEach(operation -> {
                        ApiResponses responses = operation.getResponses();
                        responses.addApiResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                                createApiResponse("Internal server error"));
                    });
        };
    }

    private List<Tag> createTags() {
        return Arrays.asList(
                new Tag().name("BankSlip").description("Manager bank slip")
        );

    }

    private ApiResponse createApiResponse(String description) {
        return new ApiResponse().description(description);
    }
}
