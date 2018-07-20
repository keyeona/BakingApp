package com.keyeonacole.bakingapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by keyeona on 7/19/18.
 */

public class RecipeAdapter extends ArrayAdapter<Recipe> {
    private Activity activity;
    private ArrayList<Recipe> lRecipe;
    private static LayoutInflater inflater = null;

    public RecipeAdapter(Activity activity, int textViewResourceId, ArrayList<Recipe> _lRecipe) {
        super(activity, textViewResourceId);
        try {
            this.activity = activity;
            this.lRecipe = _lRecipe;
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return lRecipe.size();
    }


    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public ListView display_name;
        public TextView display_number;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.fragment_main, null);
                holder = new ViewHolder();
                holder.display_name = (ListView) vi.findViewById(R.id.recipe_name_tv);

                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            //holder.display_name.setText(lProducts.get(position).name);
            //holder.display_number.setText(lProducts.get(position).number);


        } catch (Exception e) {


        }
        return vi;
    }
}
