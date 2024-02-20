package com.nl.cgi.payments.recipeapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception for LogAnalyser Endpoint
 */
@Getter
@AllArgsConstructor
public class LogAnalyserException extends RuntimeException {

    final String exceptionMessage;
    final HttpStatus httpStatus;
    final String additionalInformation;

}
