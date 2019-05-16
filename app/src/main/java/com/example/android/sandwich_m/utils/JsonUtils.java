package com.example.android.sandwich_m.utils;

import android.util.Log;


import com.example.android.sandwich_m.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils
{
    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE  = "image";
    private static final String INGREDIENTS = "ingredients";

    private static final String TAG = JsonUtils.class.getName();
    public static Sandwich parseSandwichJson(String json) throws JSONException
    {

        Log.d(TAG,json);
        JSONObject mainJsonObject = new JSONObject(json);

        JSONObject nameJsonObject = mainJsonObject.getJSONObject(NAME);

        String mainNameExtracted = nameJsonObject.getString(MAIN_NAME);
        JSONArray alsoKnownAsExtracted = nameJsonObject.getJSONArray(ALSO_KNOWN_AS);

        String placeOfOriginExtracted = mainJsonObject.getString(PLACE_OF_ORIGIN);
        String descriptionExtracted = mainJsonObject.getString(DESCRIPTION);
        String imageExtracted = mainJsonObject.getString(IMAGE);

        JSONArray ingredientsArray = mainJsonObject.getJSONArray(INGREDIENTS);

        return new Sandwich(mainNameExtracted
                ,getArrayList(alsoKnownAsExtracted)
                ,placeOfOriginExtracted
        ,descriptionExtracted
                ,imageExtracted
        ,getArrayList(ingredientsArray));
    }
    private static List<String> getArrayList(JSONArray array)
    {
        List<String> alsowKnownAs = new ArrayList<>();
        for (int x = 0;x<array.length();x++)
        {
            try {
                alsowKnownAs.add(array.getString(x));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return alsowKnownAs;
    }


}
