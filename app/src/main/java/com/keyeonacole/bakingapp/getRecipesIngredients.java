package com.keyeonacole.bakingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by keyeona on 7/29/18.
 */

public class getRecipesIngredients extends MainFragment{
    private RequestQueue MQUEUE;
    private String RECIPES_URL_STRING = null;
    private String NAME_VALUE_PAIRS = null;

    public getRecipesIngredients()  throws IOException {
        final JsonParser parser = new JsonParser();
        final  String nameValuePairs = getResources().getString(R.string.name_value_pairs);
        RECIPES_URL_STRING = getResources().getString(R.string.recipes_json_url);
        final String values = getResources().getString(R.string.values);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, RECIPES_URL_STRING, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int length = response.length();
                for (int i = 0; i < length ; i++)
                    try {
                        //Response
                        Object responseObject = response.get(i);
                        String responseData = new Gson().toJson(responseObject);
                        JsonObject jsonObject = parser.parse(responseData).getAsJsonObject();
                        String recipeItems = parser.parse(jsonObject.get(nameValuePairs).toString()).toString();

                        //Recipe
                        Recipe recipe = new Gson().fromJson(recipeItems, Recipe.class);

                        //Steps
                        JsonObject recipeStepsObg = recipe.getSteps();
                        JsonArray recipeStepsAr = recipeStepsObg.getAsJsonArray(values);
                        String recipeStepsStr = recipeStepsAr.toString();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {}

        });
        MQUEUE.add(jsonArrayRequest);

    }

    public  String iterateThrough(String parseMe, int recipeId, int position) {
        final JsonParser parser = new JsonParser();
        JsonArray primaryArray = parser.parse(parseMe).getAsJsonArray();
        Object responseObject = primaryArray.get(position);
        String responseData = new Gson().toJson(responseObject);
        JsonObject jsonObject = parser.parse(responseData).getAsJsonObject();
        String recipeItems = parser.parse(jsonObject.get(NAME_VALUE_PAIRS).toString()).toString();
        return recipeItems;
    }
}
