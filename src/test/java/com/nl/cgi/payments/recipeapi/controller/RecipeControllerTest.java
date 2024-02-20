package com.nl.cgi.payments.recipeapi.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nl.cgi.payments.recipeapi.model.RecipeResponse;
import com.nl.cgi.payments.recipeapi.service.RecipeService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {RecipeController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class RecipeControllerTest {
    @Autowired
    private RecipeController recipeController;

    @MockBean
    private RecipeService recipeService;

    @Mock
    RecipeResponse recipeResponse;

    @Test
    void testGetAllRecipes() throws Exception {
        when(recipeService.getAllRecipes()).thenReturn(new RecipeResponse(new ArrayList<>()));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/recipes/searchAll/");

        MockMvcBuilders.standaloneSetup(recipeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"recipes\":[]}"));
    }

    @Test
    void testGetRecipesByIngredients() throws Exception {
        when(recipeService.getRecipesByIngredients(Mockito.<List<String>>any()))
                .thenReturn(new RecipeResponse(new ArrayList<>()));
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/api/recipes/searchByIngredients/")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new ArrayList<>()));

        MockMvcBuilders.standaloneSetup(recipeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"recipes\":[]}"));
    }

    @Test
    public void testGetRecipesByGivenIngredients() throws IOException {
        when(recipeService.getRecipesByIngredients(any())).thenReturn(recipeResponse);
        ResponseEntity<RecipeResponse> response = recipeController.getRecipesByIngredients(Arrays.asList("onion","butter"));
        assertNotNull(response);
    }
}
