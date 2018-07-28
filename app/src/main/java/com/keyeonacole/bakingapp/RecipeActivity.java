package com.keyeonacole.bakingapp;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import java.io.IOException;

/**
 * Created by keyeona on 7/24/18.
 */

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_main);
        DetailFragment detailFragment = null;

        try {
            detailFragment = new DetailFragment();
            FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, detailFragment);
            fragmentTransaction.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
