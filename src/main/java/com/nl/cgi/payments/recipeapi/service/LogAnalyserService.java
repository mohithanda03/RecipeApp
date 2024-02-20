package com.nl.cgi.payments.recipeapi.service;

import com.nl.cgi.payments.recipeapi.exception.LogAnalyserException;
import com.nl.cgi.payments.recipeapi.model.LogAnalyserResponse;
import com.nl.cgi.payments.recipeapi.model.LogDetails;
import com.nl.cgi.payments.recipeapi.util.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Mohit Handa
 * Class LogAnalyserService
 *  Represents the service class for the Log Analyser API
 */
@Service
@Slf4j
public class LogAnalyserService {

        @Value("${com.nl.cgi.recipe.api.log.file.path}")
        String logFilePath;
    /** <p> Extracts the log statements from the input file</p>
     * @param logLevel from the user
     * @return The response list which has the logMessageDescriptions and their occurrences count
     * @throws IOException
     */
    public LogAnalyserResponse findLogsByType(String logLevel) throws LogAnalyserException {
        List<String> logMessages = extractLogsFromFile(logLevel);
        List<LogDetails> logDetails = new ArrayList<>();
        logMessages.stream()
                .collect(Collectors.groupingBy(Function.identity(),Collectors.counting()))
                .forEach((description,count)->{
                    buildResponse(logDetails, description,count,logLevel);
                });
        return new LogAnalyserResponse(logDetails.stream()
                .sorted(Comparator.comparingLong(LogDetails::getMessageOccurrenceCount).reversed())
                .toList());

    }

    /**
     * <p> Extracts the log statements from the input file</p>*
     * @param  logLevel	description of parameter
     * @return         	description of return value
     */
    private List<String> extractLogsFromFile(String logLevel) throws LogAnalyserException {
        try {
            File file = LogUtils.readLogFile(logFilePath);
            return Files.lines(file.toPath())
                    .filter(line->line.contains(logLevel.toUpperCase()))
                    .map(LogAnalyserService::extractLogDescription)
                    .toList();
        } catch (IOException exception) {
            log.error("Exception occurred while extracting logs from the file. Exception Details: {}",exception.getMessage());
            throw new LogAnalyserException("Technical Error Occurred",
                    HttpStatus.INTERNAL_SERVER_ERROR,"Exception occurred while extracting logs from the file");
        }
    }

    /**
     * Extracts the log description from the input line.
     *
     * @param  line  the input line containing the log description
     * @return       the extracted log description
     */
    private static String extractLogDescription(String line) {
        return line.substring(line.indexOf("]")+1);
    }

    /** <p> Builds the response list which contains the logs statements and the no of occurrences</p>
     * @param logDetails Each line of the log fine
     * @param description messageDescription
     * @param count Number of times the description has occurred in the file
     * @param logLevel Input loglevel from the user
     *
     */
    private static void buildResponse(List<LogDetails> logDetails, String description, Long count,String logLevel) {
        LogDetails message = new LogDetails();
        message.setLogType(logLevel);
        message.setMessageDescription(description);
        message.setMessageOccurrenceCount(count);
        logDetails.add(message);
    }

    public List<Map.Entry<String, Long>> analyzeLogs(String logLevel, int topN) throws IOException {
        List<String> logMessages = extractLogsFromFile(logLevel);

        Map<String, Long> errorCounts = logMessages.stream()
                //.filter(line -> line.contains(logType))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return errorCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(topN)
                .toList();
    }
}
