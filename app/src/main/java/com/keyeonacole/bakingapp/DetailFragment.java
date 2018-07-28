package com.keyeonacole.bakingapp;

import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private String RECIPES_URL_STRING = null;
    private String NAME_VALUE_PAIRS = null;
    private ExpandableListViewAdapter listViewAdapter;
    private List<String> ingredientName = new ArrayList<>();
    private HashMap<String,List<String>> ingredientHashMap = new HashMap<>();
    private List<RecipeSteps> recipeStepsList = new ArrayList<>();
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

        Bundle bundle = getActivity().getIntent().getExtras();
        String ingredientsData = (String) bundle.get("ingredients");
        Bundle recipeBundle = (Bundle) bundle.get("bundle");
        String stepsData = (String) bundle.get("steps");
        Log.i("StepsData", ingredientsData);

        //Prep steps for view
        JsonObject recipeStepsObject = convertString(stepsData);
        JsonArray recipeStepsAr = recipeStepsObject.getAsJsonArray(values);
        for (int i = 0; i < recipeStepsAr.size(); i++) {
            Log.i("Iteration", iterateThrough(recipeStepsAr.toString(), i).toString());
            RecipeSteps recipeSteps = new Gson().fromJson(iterateThrough(recipeStepsAr.toString(), i), RecipeSteps.class);
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

        Log.i("HashMap :", ingredientHashMap.toString());

        // Set views
        Log.i("ingredientsData: ", ingredientsData);
        String name = (String) recipeBundle.get("name");
        String servings = (String) recipeBundle.get("servings");
        String image = (String) recipeBundle.get("image");
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

    }
}
