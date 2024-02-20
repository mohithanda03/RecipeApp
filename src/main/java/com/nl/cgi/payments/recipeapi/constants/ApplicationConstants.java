package com.nl.cgi.payments.recipeapi.constants;

public class ApplicationConstants {

  public static final String APPLICATION_JSON = "application/json";
  public static final String RECIPE_PATH = "api/recipes/";

  public static final String GET_ALL_RECIPES_PATH = "searchAll/";
  public static final String GET_RECIPES_BY_INGREDIENTS_PATH = "searchByIngredients/";

  public static final String LOGGING_PATH = "api/logAnalyser/";
  public static final String FIND_LOGS_PATH = "findLogs/{logLevel}";

  public static final String TOP_ERRORS = "top-errors/";

  private ApplicationConstants() {
  }
}
