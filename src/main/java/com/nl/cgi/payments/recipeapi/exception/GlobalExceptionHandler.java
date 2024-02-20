package com.nl.cgi.payments.recipeapi.exception;

import com.nl.cgi.payments.recipeapi.constants.LoggerConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Date;

/**
 * Global Exception Handler
 */
@RestControllerAdvice
@Component
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(LogAnalyserException.class)
    public final ResponseEntity<ErrorDetails> handleLogAnalyserException(LogAnalyserException logAnalyserException){
        return ResponseEntity
                .status(logAnalyserException.httpStatus)
                .body(logError(logAnalyserException.getExceptionMessage(),
                        logAnalyserException.getAdditionalInformation()));
    }

    @ExceptionHandler(RecipeException.class)
    public final ResponseEntity<ErrorDetails> handleRecipeException(RecipeException recipeException){
        return ResponseEntity
                .status(recipeException.httpStatus)
                .body(logError(recipeException.getExceptionMessage(),
                        LoggerConstants.NOT_APPLICABLE));

    }

    @ExceptionHandler(RecipeNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleRecipeNotFoundException(RecipeNotFoundException recipeException){
        return ResponseEntity
                .status(recipeException.httpStatus)
                .body(logError(recipeException.getExceptionMessage(),
                        "Recipe not found. Your input doesn't match any recipe in our database"));

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<ErrorDetails> handleInvalidRequestException(HttpMessageNotReadableException httpMessageNotReadableException){
        log.error("Exception occurred during Http Call. Exception details : {}",httpMessageNotReadableException.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(logError("Please provide valid input",
                        "Input value must be only string or list of strings"));

    }
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public final ResponseEntity<ErrorDetails> handleEmptyRequestException(HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException){
        log.error("Exception occurred during Http Call. Exception details : {}",httpMediaTypeNotSupportedException.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(logError("Please provide ingredient(s) as input",
                        "Input value must not be null"));
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDetails> handleException(Exception exception){
        log.error("Exception occurred in the application. Details : {}",exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(logError((exception.getMessage()),
                        LoggerConstants.NOT_APPLICABLE));

    }

    private ErrorDetails logError(String errorMessage, String errorInfo) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setTimestamp(Date.from(Instant.now()));
        errorDetails.setDetails(errorInfo);
        errorDetails.setMessage(errorMessage);
        return errorDetails;
    }

}
