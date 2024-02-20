package com.nl.cgi.payments.recipeapi.controller;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;

import com.nl.cgi.payments.recipeapi.model.LogAnalyserResponse;
import com.nl.cgi.payments.recipeapi.service.LogAnalyserService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {LogAnalyserController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class LogAnalyserControllerTest {
    @Autowired
    private LogAnalyserController logAnalyserController;

    @MockBean
    private LogAnalyserService logAnalyserService;

    @Test
    void testAnalyzeLogs() throws Exception {
        when(logAnalyserService.analyzeLogs(Mockito.<String>any(), anyInt())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder paramResult = MockMvcRequestBuilders.get("/api/logAnalyser/top-errors/")
                .param("logLevel", "DEBUG");
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("topN", String.valueOf(1));

        MockMvcBuilders.standaloneSetup(logAnalyserController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testFindLogs() throws Exception {
        when(logAnalyserService.findLogsByType(Mockito.<String>any()))
                .thenReturn(new LogAnalyserResponse(new ArrayList<>()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/logAnalyser/findLogs/{logLevel}",
                "DEBUG");

        MockMvcBuilders.standaloneSetup(logAnalyserController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"logDetails\":[]}"));
    }
}
