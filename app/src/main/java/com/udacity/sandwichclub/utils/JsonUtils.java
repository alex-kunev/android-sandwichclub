package com.udacity.sandwichclub.utils;

import android.net.Uri;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class JsonUtils {


    public static Sandwich parseSandwichJson(String json) {
        {
            Sandwich sandwich = new Sandwich();

            String mainName;
            String placeOfOrigin;
            String description;
            String image;
            ArrayList<String> ingredientsList = new ArrayList<>();
            ArrayList<String> akaList = new ArrayList<>();

            if (json != null) {
                try {
                    JSONObject sandwichJson = new JSONObject(json);

                    //parsing JsonObject:
                    JSONObject JsonObjName = sandwichJson.getJSONObject("name");
                    //Get JsonObject's mainName ("Ham & Cheese", etc):
                    mainName = JsonObjName.getString("mainName");
                    //Get JsonObject's placeOfOrigin (i.e. "Uruguay", etc)
                    placeOfOrigin = sandwichJson.getString("placeOfOrigin");
                    //Get JsonObject's description:
                    description = sandwichJson.getString("description");
                    //Get JsonObject's image link:
                    image = sandwichJson.getString("image");

                    //Parse through an array of ingredients:
                    JSONArray ingredients = sandwichJson.getJSONArray("ingredients");
                    for (int i = 0; i < ingredients.length(); i++){
                        ingredientsList.add(ingredients.getString(i));
                    }
                    //Parse through an array of alsoKnownAs:
                    JSONArray alsoKnownAs = JsonObjName.getJSONArray("alsoKnownAs");
                    for (int i = 0; i < alsoKnownAs.length(); i++){
                        akaList.add(alsoKnownAs.getString(i));
                    }

                    //Set name for each sandwich item:
                    sandwich.setMainName(mainName);
                    sandwich.setAlsoKnownAs(akaList);
                    sandwich.setIngredients(ingredientsList);
                    sandwich.setImage(image);
                    sandwich.setPlaceOfOrigin(placeOfOrigin);
                    sandwich.setDescription(description);

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }

            return sandwich;
        }

    }


}
