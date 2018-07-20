package com.keyeonacole.bakingapp;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.gson.annotations.SerializedName;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String RECIPES_URL_STRING = null;
    private String NAME_VALUE_PAIRS = null;
    public ArrayList<String> recipeList;
    public ArrayList<String> recipeNames;

    public RecipeAdapter recipeAdb;
    private RequestQueue MQUEUE;


    //Recipe
    Recipe mRecipe;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MainFragment() throws IOException {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) throws IOException {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MQUEUE = Volley.newRequestQueue(getActivity().getApplicationContext());
        RECIPES_URL_STRING = getResources().getString(R.string.recipes_json_url);
        NAME_VALUE_PAIRS = getResources().getString(R.string.name_value_pairs);
        ArrayList<Recipe> recipeList  = new ArrayList<Recipe>();
        ArrayList<String> recipeNames  = new ArrayList<String>();

        Recipe[] recipes = {};

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.recipe_name_tv);


        //TODO: Delete before submitting
        //When you are on the ROAD
        //Recipe recipe = new Gson().fromJson(String.valueOf(inputStream), Recipe.class);
        try {
            readUrl(RECIPES_URL_STRING);
            //recipeAdb = new RecipeAdapter (getActivity(), 0, recipeList);
            ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, recipeNames);
            listView.setAdapter(recipeAdb);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        void onFragmentInteraction(Uri uri);
    }



    public class RecipeStepsList {
        @SerializedName("id")
        public List<RecipeSteps> recipeStepsList;
    }
    //TODO: Delete before submitting
    //http://blog.nkdroidsolutions.com/json-parsing-from-assets-using-gson-in-android-tutorial/
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("json.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void readUrl(String url) throws Exception{
        final JsonParser parser = new JsonParser();
        final  String nameValuePairs = getResources().getString(R.string.name_value_pairs);
        final String values = getResources().getString(R.string.values);
        final ArrayList<String> recipeNames  = new ArrayList<String>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int length = response.length();
                //iterateThrough(response.toString());
                Log.i("Length", String.valueOf(response.length()));
                for (int i = 0; i < length ; i++) {
                    try {
                        //Response
                        Object responseObject = response.get(i);
                        String responseData = new Gson().toJson(responseObject);
                        JsonObject jsonObject = parser.parse(responseData).getAsJsonObject();
                        String recipeItems = parser.parse(jsonObject.get(nameValuePairs).toString()).toString();
                        //Recipe
                        Recipe recipe = new Gson().fromJson(recipeItems, Recipe.class);
                        //recipeList.add(recipe);
                        recipeNames.add(recipe.getName());


                        //Ingredients
                        JsonObject recipeIngredientsObg = recipe.getIngredients();
                        JsonArray recipeIngredients = recipeIngredientsObg.getAsJsonArray(values);
                        String recipeIngredientsStr = recipeIngredients.toString();
                        for (int j = 0; j <  recipeIngredients.size(); j++) {
                            iterateThrough(recipeIngredientsStr,i,j);
                        }
                        //Steps
                        JsonObject recipeStepsObg = recipe.getSteps();
                        JsonArray recipeStepsAr = recipeStepsObg.getAsJsonArray(values);
                        String recipeStepsStr = recipeStepsAr.toString();
                        Log.i("Steps", recipeStepsObg.toString());
                        for (int j = 0; j <  recipeStepsAr.size(); j++) {
                            String sendToClass = iterateThrough(recipeStepsStr,i,j);
                            RecipeSteps recipeStepsClass = new Gson().fromJson(sendToClass, RecipeSteps.class);
                            recipeStepsClass.setRecipeName(recipe.getName());
                            Log.i("ClassSteps", recipeStepsClass.getShortDesc());
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

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
                Log.i("Test", recipeItems);
                 return recipeItems;
        }
}