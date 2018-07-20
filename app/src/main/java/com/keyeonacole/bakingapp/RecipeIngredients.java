package com.keyeonacole.bakingapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by keyeona on 7/11/18.
 */

public class RecipeIngredients {

    String recipeName;
    @SerializedName("quantity")
    int quantity;
    @SerializedName("measure")
    String measure;
    @SerializedName("ingredient")
    String ingredient;



    public RecipeIngredients(String recipeName, int quantity, String measure, String ingredient, String videoURL, String thumbnailURL){
        this.recipeName = recipeName;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;

    }

    public String getRecipeName() {return recipeName;}

    public void setRecipeName(String recipeName) {this.recipeName = recipeName;}

    public int getquantity(){return quantity;}

    public void setquantity(int quantity) {this.quantity = quantity;}

    public String getingredient() {return ingredient;}

    public void setingredient(String ingredient) {this.ingredient = ingredient;}

    public String getmeasure() {return measure;}

    public void setmeasure(String measure) {this.measure = measure;}

}
