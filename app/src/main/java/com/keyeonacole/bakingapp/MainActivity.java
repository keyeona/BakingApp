package com.keyeonacole.bakingapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.io.IOException;

import butterknife.BindView;


// Suggestions: GJSON, SNACKBARS, LOG.(e...d), TIMBER,
// Principles: Do not hard code strings
// Test often


public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener {
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //if ()
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainFragment mainFragment = null;

        try {
            mainFragment = new MainFragment();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, mainFragment)
                    .commit();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFragmentInteraction(int i, Recipe recipe) {
                //Toast.makeText(this,"postion Clicked " + i , Toast.LENGTH_SHORT).show();

                int id =recipe.getId();
                String name = recipe.getName();
                String servings = recipe.getServings();
                String image = recipe.getImage();
                JsonObject ingredients = recipe.getIngredients();
                JsonObject steps = recipe.getSteps();


                Bundle b = new Bundle();
                b.putString("name", name);
                b.putString("servings",servings);
                b.putString("image",image);
                b.putString("id", String.valueOf(id));


                final Intent intent = new Intent(this, RecipeActivity.class);
                intent.putExtra("bundle", b);
                intent.putExtra("ingredients", ingredients.toString());
                intent.putExtra("steps", steps.toString());
                startActivity(intent);

    }
}
