package com.nl.cgi.payments.recipeapi.model;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ingredients {
    @NotBlank(message = "Ingredient must not be blank")
    private String name;
}
