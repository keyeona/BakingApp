package com.keyeonacole.bakingapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by keyeona on 7/11/18.
 */

public class RecipeSteps{
    @SerializedName("name")
    String recipeName;
    @SerializedName("id")
    int stepId;
    @SerializedName("shortDescription")
    String shortDesc;
    @SerializedName("description")
    String fullDesc;
    @SerializedName("videoURL")
    String videoURL;
    @SerializedName("thumbnailURL")
    String thumbnailURL;


    public RecipeSteps(String recipeName, int stepId, String shortDesc, String fullDesc, String videoURL, String thumbnailURL){
        this.recipeName = recipeName;
        this.stepId = stepId;
        this.shortDesc = shortDesc;
        this.fullDesc = fullDesc;
        this.videoURL = videoURL;
        this.thumbnailURL =thumbnailURL;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getStepId(){
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public String getFullDesc() {
        return fullDesc;
    }

    public void setFullDesc(String fullDesc) {
        this.fullDesc = fullDesc;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
}
