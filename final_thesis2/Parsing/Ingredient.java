package com.emmanbraulio.final_thesis2.Parsing;

import java.util.ArrayList;
import java.util.List;

public class Ingredient {

    private String name;
    private int count = 0;
    private List<String> occurrences = new ArrayList<>();


    public void setName(String ingredName){
            this.name = ingredName;
    }

    public String getName(){
        return this.name;
    }

    public void addCount(){
        this.count++;
    }

    public int getCount(){
        return this.count;
    }

    public void addOccurence(String newOccurence){
        this.occurrences.add(newOccurence);
    }

    public List<String> getOccurences(){
        return this.occurrences;
    }
}
