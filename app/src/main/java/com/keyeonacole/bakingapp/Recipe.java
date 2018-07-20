package com.keyeonacole.bakingapp;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * Created by keyeona on 7/11/18.
 */

public class Recipe {
    //GSON Tutorial https://medium.com/@mananwason/how-to-parse-json-assets-with-gson-7b19dec6a34
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    @SerializedName("ingredients")
    JsonObject ingredients;

    @SerializedName("steps")
    JsonObject steps;

    @SerializedName("servings")
    String servings;

    @SerializedName("image")
    String image;


    public Recipe(int id, String name, JsonObject ingredients, JsonObject steps, String servings, String image){
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this .servings = servings;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(){
        this.name = name;
    }

    public JsonObject getIngredients(){
        return ingredients;
    }

    public void setIngredients(){
        this.ingredients = ingredients;
    }

    public JsonObject getSteps() {
        return steps;
    }

    public void setSteps(JsonObject steps) {
        this.steps = steps;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}


