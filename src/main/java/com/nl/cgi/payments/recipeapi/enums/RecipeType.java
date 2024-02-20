package com.nl.cgi.payments.recipeapi.enums;

public enum RecipeType {

    OperationType("RecipeApi");

    private final String RecipeType;

    private RecipeType(final String RecipeType) {
        this.RecipeType = RecipeType;
    }
    public String getType() {
        return RecipeType;
    }
}
