package com.nl.cgi.payments.recipeapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Recipe {

    String title;
    String href;
    String[] ingredients;
    String thumbnail;
}
