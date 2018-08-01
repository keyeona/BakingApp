package com.keyeonacole.bakingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by keyeona on 7/21/18.
 */

public class DetailFragment extends Fragment implements ExpandableListView.OnGroupExpandListener, ExpandableListView.OnGroupCollapseListener, ExpandableListView.OnChildClickListener,  RecyclerRecipeAdapter.ItemClickListener {
    @BindView(R.id.recipe_Title_tv) TextView title;
    @BindView(R.id.servings_data_tv) TextView servingData;
    @BindView(R.id.ingredients_exp_list) ExpandableListView ingredientsExpLv;
    @BindView(R.id.steps_rlv) RecyclerView stepsLV;
    @BindView(R.id.recipe_image) ImageView imageView;
    @BindView(R.id.exoplayerview_activity_video) PlayerView videoPlayer;
    @BindView(R.id.recipe_step_full_description) TextView stepDescription;
    @BindView(R.id.scrollView) ScrollView scrollView;
    private String RECIPES_URL_STRING = null;
    private String NAME_VALUE_PAIRS = null;
    private ExpandableListViewAdapter listViewAdapter;
    private List<String> ingredientName = new ArrayList<>();
    private HashMap<String,List<String>> ingredientHashMap = new HashMap<>();
    private List<RecipeSteps> recipeStepsList = new ArrayList<>();
    private List<String> stepsVideoList = new ArrayList<>();
    private SharedPreferences rPreferences;



    private RecyclerRecipeAdapter recylerAdapter;


    public DetailFragment() throws IOException{
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        NAME_VALUE_PAIRS = getResources().getString(R.string.name_value_pairs);
        final String values = getResources().getString(R.string.values);
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        ButterKnife.setDebug(true);
        Bundle bundle = getArguments();
        //Bundle bundle = getActivity().getIntent().getExtras();
        String ingredientsData = (String) bundle.get("ingredients");
        //Bundle recipeBundle = (Bundle) bundle.get("bundle");
        String stepsData = (String) bundle.get("steps");
        Log.i("StepsData", ingredientsData);
        android.app.ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }



        //Prep steps for view
        JsonObject recipeStepsObject = convertString(stepsData);
        JsonArray recipeStepsAr = recipeStepsObject.getAsJsonArray(values);
        for (int i = 0; i < recipeStepsAr.size(); i++) {
            Log.i("Iteration", iterateThrough(recipeStepsAr.toString(), i).toString());
            RecipeSteps recipeSteps = new Gson().fromJson(iterateThrough(recipeStepsAr.toString(), i), RecipeSteps.class);
            stepsVideoList.add(recipeSteps.getVideoURL());
            recipeStepsList.add(recipeSteps);
        }


        //Prep ingredients for view
        JsonObject ingredientsObject = convertString(ingredientsData);
        JsonArray recipeIngredients = ingredientsObject.getAsJsonArray(values);
        String recipeIngredientsStr = recipeIngredients.toString();
        for (int j = 0; j < recipeIngredients.size(); j++) {
            RecipeIngredients RI = new Gson().fromJson(iterateThrough(recipeIngredientsStr, j), RecipeIngredients.class);
            List<String> dropDownList = new ArrayList<>();
            dropDownList.add("Measurement: " + RI.getmeasure());
            dropDownList.add(String.valueOf("Quantity: " + RI.getquantity()));
            ingredientName.add(RI.getingredient());
            ingredientHashMap.put(RI.getingredient(), dropDownList);
        }


        //SharedPrefs
        SharedPreferences rPreferences  = getContext().getSharedPreferences("SaveIngredients",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = rPreferences.edit();
        Log.i("IngredientName", ingredientName.toString());
        edit.putString("ingredients", ingredientName.toString());
        Set<String> ingredientSet = new HashSet<String>(ingredientName);
        edit.putStringSet("IngredientSet", ingredientSet);
        edit.apply();
        Log.i("sharedPrefs", String.valueOf(rPreferences.getString("ingredients", "[]")));

        Log.i("HashMap :", ingredientHashMap.toString());

        // Set views
        Log.i("ingredientsData: ", ingredientsData);
        String name = (String) bundle.get("name");
        String servings = (String) bundle.get("servings");
        String image = (String) bundle.get("image");
        Glide.with(this).load(image).into(imageView);
        title.setText(name);
        servingData.setText(servings);


        //Expandable View
        ingredientsExpLv.setOnGroupExpandListener(this);
        ingredientsExpLv.setOnGroupCollapseListener(this);
        ingredientsExpLv.setOnChildClickListener(this);
        listViewAdapter = new ExpandableListViewAdapter(getContext(),ingredientName,ingredientHashMap);
        ingredientsExpLv.setAdapter(listViewAdapter);
        //RecyclerView
        stepsLV.setLayoutManager(new LinearLayoutManager(getContext()));
        recylerAdapter = new RecyclerRecipeAdapter(getContext(), recipeStepsList);
        recylerAdapter.setClickListener(this);
        stepsLV.setAdapter(recylerAdapter);
        return view;
    }


    public JsonObject convertString(String data){
        JsonObject convertedObject = null;
        JsonElement dataElement = null;
        dataElement = new JsonParser().parse(data);
        convertedObject = dataElement.getAsJsonObject();
        return convertedObject;
    }

    public void populateList(Bundle mainRecipeData){
        String name = (String) mainRecipeData.get("name");
        String servings = (String) mainRecipeData.get("servings");
        title.setText(name);
        servingData.setText(servings);
    }

    public  JsonElement iterateThrough(String parseMe, int position) {
        final JsonParser parser = new JsonParser();
        JsonArray primaryArray = parser.parse(parseMe).getAsJsonArray();
        Object responseObject = primaryArray.get(position);
        String responseData = new Gson().toJson(responseObject);
        JsonObject jsonObject = parser.parse(responseData).getAsJsonObject();
        JsonElement recipeItems = parser.parse(jsonObject.get(NAME_VALUE_PAIRS).toString());
        return recipeItems;
    }

    @Override
    public void onGroupExpand(int i) {

    }

    @Override
    public void onGroupCollapse(int i) {

    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {


        return false;
    }

    @Override
    public void onItemClick(View view, int position) {


        scrollView.setScrollY(0);
        Log.i("onClick", String.valueOf(position));
        RecipeSteps currentStep = recipeStepsList.get(position);
        Log.i("StepVideo", currentStep.getVideoURL());
        stepDescription.setText(currentStep.getFullDesc());
        stepDescription.setVisibility(View.VISIBLE);
        System.out.println(stepsVideoList.get(position));
        if (stepsVideoList.get(position).length() > 0){

        //Prepare Track Selector for Video Player
        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);

        SimpleExoPlayer player =
                ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        videoPlayer.setPlayer(player);
        videoPlayer.setVisibility(View.VISIBLE);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), "BakingApp"), (TransferListener<? super DataSource>) bandwidthMeter);

        Uri mp4VideoUri = Uri.parse(stepsVideoList.get(position));
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(mp4VideoUri);
        player.prepare(videoSource);

        }else{
            videoPlayer.setVisibility(View.GONE);
            Toast.makeText(getContext(),"Sorry No video for Step: " + (position) , Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onPause() {
        super.onPause();

        //Prepare Track Selector for Video Player
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);
        SimpleExoPlayer player =
                ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
        player.release();

    }
}
