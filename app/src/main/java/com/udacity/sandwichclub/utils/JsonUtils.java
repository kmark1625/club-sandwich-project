package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static String NAME = "name";
    private static String MAIN_NAME = "mainName";
    private static String ALSO_KNOWN_AS = "alsoKnownAs";
    private static String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static String DESCRIPTION = "description";
    private static String IMAGE = "image";
    private static String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        try {
            final JSONObject result = new JSONObject(json);
            final JSONObject name = result.getJSONObject(NAME);
            final String mainName = name.getString(MAIN_NAME);
            final List<String> alsoKnownAs = parseStringJsonArray(name.getJSONArray(ALSO_KNOWN_AS));
            final String placeOfOrigin = result.getString(PLACE_OF_ORIGIN);
            final String description = result.getString(DESCRIPTION);
            final String image = result.getString(IMAGE);
            final List<String> ingredients = parseStringJsonArray(result.getJSONArray(INGREDIENTS));
            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
        } catch(final JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<String> parseStringJsonArray(final JSONArray jsonStringArray) {
        final List<String> results = new ArrayList<>();
        for (int i=0; i < jsonStringArray.length(); i++) {
            try {
                final String value = jsonStringArray.getString(i);
                results.add(value);
            } catch (final JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
