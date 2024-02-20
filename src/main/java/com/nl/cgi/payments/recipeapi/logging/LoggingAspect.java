package com.nl.cgi.payments.recipeapi.logging;

import com.nl.cgi.payments.recipeapi.model.LogAnalyserResponse;
import com.nl.cgi.payments.recipeapi.model.RecipeResponse;
import com.nl.cgi.payments.recipeapi.constants.LoggerConstants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 *
 */
@Aspect
@Slf4j
@Component
@EnableAspectJAutoProxy
public class LoggingAspect {

    @Around("execution(* com.nl.cgi.payments.recipeapi.controller.RecipeController.getAllRecipes(..))")
    public ResponseEntity<RecipeResponse> aroundGetAllRecipes(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return (ResponseEntity<RecipeResponse>) joinPoint.proceed();
        } catch (Throwable throwable) {
            log.debug("Error occurred during method : {}. Error Details:{}" , LoggerConstants.GET_ALL_RECIPES, throwable.getStackTrace());
            throw throwable;
        }
    }

    @Around("execution(* com.nl.cgi.payments.recipeapi.controller.RecipeController.getRecipesByIngredients(..))")
    public ResponseEntity<RecipeResponse> aroundGetRecipesByIngredients(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return (ResponseEntity<RecipeResponse>) joinPoint.proceed();
        } catch (Throwable throwable) {
            log.debug("Error occurred during method : {}. Error Details:{}" , LoggerConstants.GET_RECIPES_BY_INGREDIENTS,throwable.getMessage());
            throw throwable;
        }
    }
    @Around("execution(* com.nl.cgi.payments.recipeapi.controller.LogAnalyserController.analyseLogs(..))")
    public ResponseEntity<LogAnalyserResponse> aroundLogAnalyser(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return (ResponseEntity<LogAnalyserResponse>) joinPoint.proceed();
        } catch (Throwable throwable) {
            log.debug("Error occurred during method : {}. Error Details:{}" , LoggerConstants.ANALYSE_LOG_STATEMENTS,throwable.getMessage());
            throw throwable;
        }
    }
}
