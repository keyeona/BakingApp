package com.keyeonacole.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by keyeona on 7/27/18.
 */

public class RecyclerRecipeAdapter extends RecyclerView.Adapter<RecyclerRecipeAdapter.ViewHolder> {

    private List<RecipeSteps> recipeStepsList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    RecyclerRecipeAdapter(Context context, List<RecipeSteps> steps) {
        this.mInflater = LayoutInflater.from(context);
        this.recipeStepsList = steps;
        Log.i("Steps:", steps.toString());
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.repice_steps_row, parent, false);
        ButterKnife.bind(this, view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerRecipeAdapter.ViewHolder holder, int position) {
        RecipeSteps steps = recipeStepsList.get(position);
        String id = String.valueOf(steps.getStepId() );
        holder.stepID.setText(id);
        holder.shortDescription.setText(steps.getShortDesc());
    }


    // total number of rows
    @Override
    public int getItemCount() {
        return recipeStepsList.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.step_id) TextView stepID;
        @BindView(R.id.short_description) TextView shortDescription;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    RecipeSteps getItem(int id) {
        return recipeStepsList.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}


