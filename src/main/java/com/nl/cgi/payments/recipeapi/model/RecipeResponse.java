package com.nl.cgi.payments.recipeapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
/**
 *  <p>Response Object for Recipe Endpoint</p>
 */
@Data
@AllArgsConstructor
public class RecipeResponse {
    List<Recipe> recipes;
}
