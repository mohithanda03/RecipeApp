package com.nl.cgi.payments.recipeapi.controller;


import com.nl.cgi.payments.recipeapi.constants.ApplicationConstants;
import com.nl.cgi.payments.recipeapi.enums.LogAnalyserType;
import com.nl.cgi.payments.recipeapi.exception.LogAnalyserException;
import com.nl.cgi.payments.recipeapi.model.LogAnalyserResponse;
import com.nl.cgi.payments.recipeapi.service.LogAnalyserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
     * @author Mohit Handa
     * Class LogAnalyserController
     * Represents the controller class for the LogAnalyser API
     * It has two methods
     * <li>analyseLogs</li>
     * <p> Controller class that has the endpoint to analyse logs based on user input</p>
     */
    @Slf4j
    @RestController
    @Tag(name = "Log Analyser", description = "Analysing the logs based on the input logLevel")
    @RequestMapping(value = ApplicationConstants.LOGGING_PATH)
    @Validated
    public class LogAnalyserController {

    private final LogAnalyserService logAnalyserService;

    public LogAnalyserController(LogAnalyserService logAnalyserService) {
        this.logAnalyserService = logAnalyserService;
    }

    /**
     * Display the reoccurring logs in the log file based on the input logLevel.
     *
     * @param logLevel description of parameter
     * @return List of LogDetails objects
     */
    @Operation(summary = "Display the reoccurring logs in the log file based on the input logLevel")
    @GetMapping(path = ApplicationConstants.FIND_LOGS_PATH, produces = ApplicationConstants.APPLICATION_JSON)
    public ResponseEntity<LogAnalyserResponse> findLogs(@NotNull @PathVariable("logLevel") String logLevel) throws LogAnalyserException {
        log.info("Analysing the logs based on the input logLevel: {}", logLevel);
        if (Boolean.FALSE.equals(LogAnalyserType.isValidLogLevel(logLevel))) {
            log.error("The input loglevel is not in the allowed list");
            throw new LogAnalyserException("Please enter a valid loglevel",
                    HttpStatus.BAD_REQUEST, "Your input is not allowed");
        }
        LogAnalyserResponse response = logAnalyserService.findLogsByType(logLevel);
        log.info("Successfully analysed the logs");
        return ResponseEntity.ok()
                .body(response);
    }

    /**
     * Analyze logs based on the given log type and return the top N entries.
     *
     * @param  logLevel  the type of log to analyze
     * @param  topN     the number of top entries to return
     * @return          a list of map entries containing the log type and the count
     */
    @GetMapping(path = ApplicationConstants.TOP_ERRORS, produces = ApplicationConstants.APPLICATION_JSON)
        public List<Map.Entry<String, Long>> topErrors(@RequestParam String logLevel, @RequestParam int topN) throws IOException {
        if (Boolean.FALSE.equals(LogAnalyserType.isValidLogLevel(logLevel))) {
            log.error("The input loglevel is not in the allowed list");
            throw new LogAnalyserException("Please enter a valid loglevel",
                    HttpStatus.BAD_REQUEST, "Your input is not allowed");
        }
            return logAnalyserService.analyzeLogs(logLevel, topN);
        }
}
