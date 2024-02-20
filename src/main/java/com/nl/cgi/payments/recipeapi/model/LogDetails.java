package com.nl.cgi.payments.recipeapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogDetails {

    String logType;
    String messageDescription;
    long messageOccurrenceCount;
}

