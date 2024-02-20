package com.nl.cgi.payments.recipeapi.enums;

import java.util.List;

public class LogAnalyserType
{
    public static final String DEBUG = "DEBUG";
    public static final String ERROR = "ERROR";
    public static final String FATAL = "FATAL";
    public static final String WARN = "WARN";
    public static final String INFO = "INFO";

    /**
     * Retrieves the log levels as a list of strings.
     *
     * @return          a list of strings representing log levels
     */
    public static List<String> getLogLevels() {
        return List.of(DEBUG, ERROR, FATAL, WARN, INFO);
    }

    /**
     * Check if the provided log level is valid.
     *
     * @param  logLevel	the log level to be checked
     * @return         	true if the log level is valid, false otherwise
     */
    public static boolean isValidLogLevel(String logLevel) {
        return getLogLevels().contains(logLevel);
    }
}
