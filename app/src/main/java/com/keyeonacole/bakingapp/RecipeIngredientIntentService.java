package com.keyeonacole.bakingapp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by keyeona on 7/29/18.
 */

public class RecipeIngredientIntentService extends IntentService{
    public static final String ACTION_GET_INGREDIENTS = "com.keyeonacole.bakingapp.action.getRecipes";
    public RecipeIngredientIntentService(String name) {
        super(name);
    }

    public static void startActionGetIngredients(Context context) {
        Intent intent = new Intent(context, getRecipesIngredients.class);
        intent.setAction(ACTION_GET_INGREDIENTS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_GET_INGREDIENTS.equals(action)) {
                handleActiongetIngredients();
            }
        }
    }

    private void handleActiongetIngredients(){




    }
}
