package com.nl.cgi.payments.recipeapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nl.cgi.payments.recipeapi.exception.RecipeException;
import com.nl.cgi.payments.recipeapi.exception.RecipeNotFoundException;
import com.nl.cgi.payments.recipeapi.model.Recipe;
import com.nl.cgi.payments.recipeapi.model.RecipeResponse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RecipeService.class})
@ExtendWith(SpringExtension.class)
class RecipeServiceTest {
    @MockBean
    private ObjectMapper objectMapper;

    @Autowired
    private RecipeService recipeService;

    @Test
    void testGetAllRecipes() throws IOException {
        Recipe recipe = new Recipe();
        recipe.setHref("https://recipe.com/");
        recipe.setIngredients(new String[]{"onion","butter"});
        recipe.setThumbnail("Thumbnail");
        recipe.setTitle("Butter Toast");
        when(objectMapper.readValue(Mockito.<File>any(), Mockito.<Class<Recipe[]>>any())).thenReturn(new Recipe[]{recipe});

        RecipeResponse actualAllRecipes = recipeService.getAllRecipes();
        verify(objectMapper).readValue(Mockito.<File>any(), Mockito.<Class<Recipe[]>>any());
        assertEquals(1, actualAllRecipes.getRecipes().size());
    }

    @Test
    public void testGetRecipesForIOException() throws IOException {
        when(objectMapper.readValue((File) any(), eq(Recipe[].class))).thenThrow(IOException.class);
        assertThrows(RecipeException.class,()->recipeService.getRecipesByIngredients(Arrays.asList("onion")));
        verify(objectMapper,times(1)).readValue((File) any(), eq(Recipe[].class));
    }

    @Test
    void testGetRecipesByIngredientsAnything() throws IOException {
        when(objectMapper.readValue(Mockito.<File>any(), Mockito.<Class<Recipe[]>>any())).thenReturn(getRecipes());
        assertThrows(RecipeNotFoundException.class, () -> recipeService.getRecipesByIngredients(new ArrayList<>()));
        verify(objectMapper).readValue(Mockito.<File>any(), Mockito.<Class<Recipe[]>>any());
    }

    public static Recipe[] getRecipes(){
        Recipe recipe = new Recipe();
        recipe.setHref("https://recipe.com/");
        recipe.setIngredients(new String[]{"onion","butter"});
        recipe.setThumbnail("Thumbnail");
        recipe.setTitle("Butter Toast");
        return new Recipe[]{recipe};
    }

}
