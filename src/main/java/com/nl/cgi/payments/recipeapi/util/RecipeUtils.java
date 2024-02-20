package com.nl.cgi.payments.recipeapi.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**<p> Util class that reads the file from the class path</p>
 *
 */
@Slf4j
@Component
public class RecipeUtils {

    private static File recipeFile;

    /**<p> Method that reads the log file from the path</p>
     * @param filePath path of the recipe json file that needs to be parsed
     * @return Parsed file content
     */
    public static File readRecipeFile(String filePath) {
        try {
            if(null==recipeFile){
                //File will be loaded only once and stay in the static memory till the app is restarted.
                //This will save the ResourceUtils.getFile call for every request.
                recipeFile = ResourceUtils.getFile(filePath);
                log.info("Loaded the Recipe json file successfully");
            }
        } catch (FileNotFoundException e) {
            //Do not throw the error
            log.error("Recipe Json file not found");
        }
        return recipeFile;
    }
}
