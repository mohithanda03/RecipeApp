package com.nl.cgi.payments.recipeapi.service;

import com.nl.cgi.payments.recipeapi.exception.RecipeException;
import com.nl.cgi.payments.recipeapi.exception.RecipeNotFoundException;
import com.nl.cgi.payments.recipeapi.model.Recipe;
import com.nl.cgi.payments.recipeapi.model.RecipeResponse;
import com.nl.cgi.payments.recipeapi.util.RecipeUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author Mohit Handa
 * Class RecipeService
 *  Represents the service class for the Recipe API. It has two methods
 *  <li>getAllRecipes</li>
 *  <li>getRecipesByIngredients</li>
 */

@Slf4j
@Service
public class RecipeService {
    private final ObjectMapper objectMapper;

    public RecipeService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Value("${com.nl.cgi.recipe.api.recipe.file.path}")
    String recipeFilePath;

    /**
     * @return sorted list of the recipes, loaded from the file
     * @throws IOException
     */
    public RecipeResponse getAllRecipes() throws IOException {
        File file = RecipeUtils.readRecipeFile(recipeFilePath);
        List<Recipe> recipes = Arrays.asList(objectMapper.readValue(file, Recipe[].class));
        return new RecipeResponse(recipes.stream()
                .sorted(Comparator.comparing(Recipe::getTitle))
                .toList());

    }

    /**
     * @param ingredients list of ingredients
     * @return list of recipes based on the input ingredients
     */
    public RecipeResponse getRecipesByIngredients(List<String> ingredients) {
        File file = RecipeUtils.readRecipeFile(recipeFilePath);
        List<Recipe> recipes = getRecipes(file);
        //Filter the recipes bases on input ingredients and sort them
        List<Recipe> filteredRecipes = recipes.stream()
                .filter(recipe-> Arrays.stream(recipe.getIngredients())
                        .anyMatch(ingredients::contains))
                .sorted(Comparator.comparing(Recipe::getTitle))
                .toList();
        //Throw exception if the input ingredient is invalid
        if(filteredRecipes.isEmpty()){
            log.error("Recipe not found. Your input doesn't match any recipe");
            throw new RecipeNotFoundException("Please enter a valid ingredient. Your input doesn't match",
                    HttpStatus.BAD_REQUEST);
        }
        return new RecipeResponse(filteredRecipes);
    }

    private List<Recipe> getRecipes(File file) {
        List<Recipe> recipes;
        try {
            recipes = Arrays.asList(objectMapper.readValue(file, Recipe[].class));
        } catch (IOException exception) {
            log.error("Error occurred while retrieving recipe details. Exception details: {}",
                    exception.getMessage());
            throw new RecipeException("Error occurred while retrieving the recipes",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return recipes;
    }
}
