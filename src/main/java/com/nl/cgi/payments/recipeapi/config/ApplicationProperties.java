package com.nl.cgi.payments.recipeapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "com.nl.cgi.recipe.api.application-properties")
@Data
public class ApplicationProperties {
    private RecipeProperties async;

    @Data
    public static class RecipeProperties {

        private int recipe_path;
        private int recipe_file_name;
        private int maxPoolSize;
        private String threadNamePrefix;
    }
}
