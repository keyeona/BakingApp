package com.keyeonacole.bakingapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.gson.JsonObject;

import java.io.IOException;

import butterknife.BindView;


// Suggestions: GJSON, SNACKBARS, LOG.(e...d), TIMBER,
// Principles: Do not hard code strings
// Test often


public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener {
    @BindView(R.id.tablet_view) FrameLayout tableView;
    @BindView(R.id.fragmentContainerRight) FrameLayout containerRight;
    private boolean mTwoPane;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);

        }
        //SharesPreferences Listener

        SharedPreferences rPreferences  = getSharedPreferences("SaveIngredients", Context.MODE_PRIVATE);
        rPreferences.registerOnSharedPreferenceChangeListener(listener);
        //Why does this always return false; What am I doing wrong?
        if (findViewById(R.id.tablet_view) != null){
            mTwoPane = true;
        }else{
            mTwoPane = false;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainFragment mainFragment = null;
        try {
            rPreferences.registerOnSharedPreferenceChangeListener(listener);
            Log.i("Tablet?", String.valueOf(mTwoPane));
            mainFragment = new MainFragment();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            if (savedInstanceState == null ) {
                fragmentManager.beginTransaction()
                        .add(R.id.fragmentContainer, mainFragment)
                        .commit();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

                      rPreferences.registerOnSharedPreferenceChangeListener(listener);


    }

    @Override
    public void onFragmentInteraction(int i, Recipe recipe) {
                //Toast.makeText(this,"postion Clicked " + i , Toast.LENGTH_SHORT).show();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);
                SimpleExoPlayer player =
                ExoPlayerFactory.newSimpleInstance(this, trackSelector);
                player.release();
        SharedPreferences rPreferences  = getSharedPreferences("SaveIngredients", Context.MODE_PRIVATE);
        rPreferences.registerOnSharedPreferenceChangeListener(listener);



        if (findViewById(R.id.tablet_view) != null){
                    try {
                        DetailFragment detailFragment = new DetailFragment();

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
                        b.putString("ingredients", ingredients.toString());
                        b.putString("steps", steps.toString());
                        detailFragment.setArguments(b);

                        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainerRight, detailFragment)
                                .commit();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }else{

                    try {
                        DetailFragment detailFragment = new DetailFragment();

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
                        b.putString("ingredients", ingredients.toString());
                        b.putString("steps", steps.toString());
                        detailFragment.setArguments(b);

                        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainer, detailFragment)
                                .addToBackStack("home")
                                .commit();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        try {
            DetailFragment detailFragment = new DetailFragment();
            getSupportFragmentManager().putFragment(outState, "myFragmentName",detailFragment);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
            Log.i("PreferenceChanged", key);
            Intent intent = new Intent(getApplicationContext(), BakingAppIngredientWidget.class);
            intent.setAction("AppWidgetManager.ACTION_APPWIDGET_UPDATE");
            sendBroadcast(intent);
        }
    };
}
