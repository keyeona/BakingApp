package com.keyeonacole.bakingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Created by keyeona on 7/30/18.
 */
////https://medium.com/@puruchauhan/android-widget-for-starters-5db14f23009b
public class WidgetDataModel {

    public String title = "";

    public static ArrayList<WidgetDataModel> getDataFromSharedPrefs(Context context) {
        ArrayList<WidgetDataModel> list = new ArrayList<>();
        list.clear();
        if(list.isEmpty()) {
            SharedPreferences sharedPref = context.getSharedPreferences("SaveIngredients", Context.MODE_PRIVATE);
            Set<String> ingredientSet = new HashSet<String>();
            String spiltMe = sharedPref.getString("ingredients", "");
            Log.i("String", spiltMe);
            List<String> items = Arrays.asList(spiltMe.split("\\s*,\\s*"));

            JsonElement json = new Gson().toJsonTree(items);
            JsonArray json2 = json.getAsJsonArray();
            //Log.i("test", json2);

             for (int i = 0; i < json2.size(); i++) {
                 WidgetDataModel model = new WidgetDataModel();
                 model.title = json2.get(i).toString();
                 list.add(model);
                }
        }
        return list;
    }

}