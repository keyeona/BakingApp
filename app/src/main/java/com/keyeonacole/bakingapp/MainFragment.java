package com.keyeonacole.bakingapp;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private String RECIPES_URL_STRING = null;
    private String NAME_VALUE_PAIRS = null;
    public List<String> MrecipeNames;
    public RecipeAdapter recipeAdb;
    private RequestQueue MQUEUE;
    private OnFragmentInteractionListener mListener;



    public MainFragment() throws IOException {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MQUEUE = Volley.newRequestQueue(getActivity().getApplicationContext());
        RECIPES_URL_STRING = getResources().getString(R.string.recipes_json_url);
        NAME_VALUE_PAIRS = getResources().getString(R.string.name_value_pairs);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        //ListView listView = (ListView) rootView.findViewById(R.id.recipe_name_tv);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        MrecipeNames = new ArrayList<String>();


        try {
            readUrl(RECIPES_URL_STRING, inflater, rootView, recyclerView);
            //ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, MrecipeNames);
            //listView.setAdapter(listAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int postions, Recipe recipe);

    }

    private void readUrl(String url, final LayoutInflater inflater, final View rootView, final RecyclerView recyclerView) throws Exception{
        final JsonParser parser = new JsonParser();
        final  String nameValuePairs = getResources().getString(R.string.name_value_pairs);
        final String values = getResources().getString(R.string.values);
        final List<Recipe> listOfRecipes = new ArrayList<Recipe>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int length = response.length();
                for (int i = 0; i < length ; i++)
                    try {
                        //Response
                        Object responseObject = response.get(i);
                        String responseData = new Gson().toJson(responseObject);
                        JsonObject jsonObject = parser.parse(responseData).getAsJsonObject();
                        String recipeItems = parser.parse(jsonObject.get(nameValuePairs).toString()).toString();

                        //Recipe
                        Recipe recipe = new Gson().fromJson(recipeItems, Recipe.class);
                        listOfRecipes.add(recipe);
                        MrecipeNames.add(recipe.getName());

                        //Steps
                        JsonObject recipeStepsObg = recipe.getSteps();
                        JsonArray recipeStepsAr = recipeStepsObg.getAsJsonArray(values);
                        String recipeStepsStr = recipeStepsAr.toString();

                        for (int j = 0; j < recipeStepsAr.size(); j++) {
                            String sendToClass = iterateThrough(recipeStepsStr, i, j);
                            RecipeSteps recipeStepsClass = new Gson().fromJson(sendToClass, RecipeSteps.class);
                            recipeStepsClass.setRecipeName(recipe.getName());
                            //Log.i("ClassSteps", recipeStepsClass.getShortDesc());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                populateUI((ArrayList<String>) MrecipeNames,inflater,rootView, listOfRecipes, recyclerView);

            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {}

        });
        MQUEUE.add(jsonArrayRequest);

    }

    public  String iterateThrough(String parseMe, int recipeId, int position) {
        final JsonParser parser = new JsonParser();
        JsonArray primaryArray = parser.parse(parseMe).getAsJsonArray();
                Object responseObject = primaryArray.get(position);
                String responseData = new Gson().toJson(responseObject);
                JsonObject jsonObject = parser.parse(responseData).getAsJsonObject();
                String recipeItems = parser.parse(jsonObject.get(NAME_VALUE_PAIRS).toString()).toString();
                return recipeItems;
        }



    public void populateUI(ArrayList<String> recipeName, final LayoutInflater inflater, View rootView,  final List<Recipe> listOfRecipes, RecyclerView recyclerView){
        //ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, MrecipeNames);
        //listView.setAdapter(listAdapter);
        //listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
        //            @Override
        //            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //                DetailFragment detailFragment = null;
        //                mListener.onFragmentInteraction(i, listOfRecipes.get(i));
        //
        //            }
        //});

        //RecyclerView
        //listView.setVisibility(View.GONE);


        MainFragmentAdapter recipeAdapter = new MainFragmentAdapter(getContext(), MrecipeNames);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(recipeAdapter);
        recipeAdapter.setClickListener(new MainFragmentAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("MainRecycler", String.valueOf(position));
                DetailFragment detailFragment = null;
                mListener.onFragmentInteraction(position, listOfRecipes.get(position));
                Log.i("Count", String.valueOf(MrecipeNames.size()));
            }
        });



    }



}