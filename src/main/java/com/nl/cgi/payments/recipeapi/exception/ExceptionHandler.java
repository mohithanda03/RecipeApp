package com.nl.cgi.payments.recipeapi.exception;


import com.nl.cgi.payments.recipeapi.enums.RecipeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExceptionHandler {
    public void handleError(Exception e) {

        log.error(String.format("Error while %s", RecipeType.OperationType.getType()) + e.getMessage(), e.getCause());
    }
}
