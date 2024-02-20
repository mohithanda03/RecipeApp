package com.nl.cgi.payments.recipeapi.controller;

import com.nl.cgi.payments.recipeapi.constants.ApplicationConstants;
import com.nl.cgi.payments.recipeapi.model.RecipeResponse;
import com.nl.cgi.payments.recipeapi.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author Mohit Handa
 * Class RecipeController
 *  Represents the controller class for the Recipe API
 *  It has two methods
 *  <li>getAllRecipes</li>
 *  <li>getRecipesByIngredients</li>
 */
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Tag(name="Recipe API", description = "Recipe management APIs")
@RequestMapping(ApplicationConstants.RECIPE_PATH)
@Validated
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     * Gets all the recipes from file
     * @return List of all available recipes.
     */
    @Operation(summary = "Retrieve all the Recipes")
    @GetMapping(produces= ApplicationConstants.APPLICATION_JSON,path = ApplicationConstants.GET_ALL_RECIPES_PATH)
    public ResponseEntity<RecipeResponse> getAllRecipes() throws IOException {
        log.info("Fetching all the recipes");
        RecipeResponse recipes = recipeService.getAllRecipes();
        log.info("Successfully retrieved all the recipes");
        return ResponseEntity.ok()
                .body(recipes);
    }
    /**
     * Gets the recipes that match the input ingredients
     * @param ingredients list of ingredients
     * @return Recipes that contain the input ingredients
     */
    @Operation(summary = "Search for recipes by a ingredients",
            description = "Searches the recipes for list of ingredients and returns the matching recipes")
    @PostMapping(produces= ApplicationConstants.APPLICATION_JSON,consumes = ApplicationConstants.APPLICATION_JSON
            ,path = ApplicationConstants.GET_RECIPES_BY_INGREDIENTS_PATH)
    public ResponseEntity<RecipeResponse> getRecipesByIngredients(@RequestBody @NotNull @Valid List<String> ingredients) {
        log.info("Searching for recipes by Ingredients");
        RecipeResponse recipes = recipeService.getRecipesByIngredients(ingredients);
        log.info("Successfully retrieved recipes by Ingredients");
        return ResponseEntity.ok()
                .body(recipes);
    }
}
