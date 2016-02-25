package com.example.kelvs.parsing;

import java.util.ArrayList;
import java.util.List;


public class Match {

    private String name;
    private String sourceURL;
    private List<String> ingredients = new ArrayList<>();

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getSourceURL(){
        return sourceURL;
    }

    public void setSourceURL(String sourceURL){
        this.sourceURL = sourceURL;
    }

    public void addIngredients(String ingredient){
        this.ingredients.add(ingredient);
    }

    public List<String> getIngredients(){
        return this.ingredients;
    }

    public void showIngredients(){
        for(String ingredient : ingredients)
            System.out.println(ingredient);
    }

    public void setIngredients(List<String> ingredients){
        this.ingredients = ingredients;
    }
}
