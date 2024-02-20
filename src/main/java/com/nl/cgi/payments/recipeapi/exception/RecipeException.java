package com.nl.cgi.payments.recipeapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception for recipe endpoint
 */
@Getter
@AllArgsConstructor
public class RecipeException extends RuntimeException{
    final String exceptionMessage;
    final HttpStatus httpStatus;

}
