package com.nl.cgi.payments.recipeapi.service;

import com.nl.cgi.payments.recipeapi.exception.LogAnalyserException;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration(classes = {LogAnalyserService.class})
@ExtendWith(SpringExtension.class)
class LogAnalyserServiceTest {
    @Autowired
    private LogAnalyserService logAnalyserService;


    @Test
    void testFindLogsByType() throws LogAnalyserException {
        assertThrows(LogAnalyserException.class, () -> logAnalyserService.findLogsByType("Log Level"));
    }

    @Test
    void testAnalyzeLogs() throws IOException {
        assertThrows(LogAnalyserException.class, () -> logAnalyserService.analyzeLogs("Log Level", 1));
        assertThrows(LogAnalyserException.class,
                () -> logAnalyserService.analyzeLogs("Loaded the Log file successfully", 1));
    }
}
