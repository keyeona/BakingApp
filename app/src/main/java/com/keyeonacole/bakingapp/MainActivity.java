package com.keyeonacole.bakingapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;


// Suggestions: GJSON, SNACKBARS, LOG.(e...d), TIMBER,
// Principles: Do not hard code strings
// Test often


public class MainActivity extends FragmentActivity implements MainFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        MainFragment mainFragment = null;
        try {
            mainFragment = new MainFragment();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_name_tv, mainFragment)
            .commit();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
