package com.nl.cgi.payments.recipeapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 *  <p>Response Object for Log Analyser Endpoint</p>
 */
@Data
@AllArgsConstructor
public class LogAnalyserResponse {

    List<LogDetails> logDetails;
}
