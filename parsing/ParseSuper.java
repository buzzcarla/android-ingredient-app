package com.example.kelvs.parsing;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public abstract class ParseSuper {

    /*
        declaring JSONresponse will later on make algo faster by only parsing
        the ingredients when the API returns unique results
    */

    public JSONObject JSONresponse = null;
    public List<Match> matches = new ArrayList<>();
    public int matchCount = 0;


    public abstract String setRequestURL(String keyword);

    private void setJSONResponse(String requestURL){

        Request request = new Request.Builder()
                .url(requestURL)
                .build();

        OkHttpClient client = new OkHttpClient();

        try {
            Response response = client.newCall(request).execute();

            if (response != null) try {

                this.JSONresponse = new JSONObject(response.body().string());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* sub-class will override this method with specific parsing algo for API */
    public abstract void setMatches(String keyword, List<String> sources, int matchCountTotal);

    public void parseJSON(String keyword, List<String> sources, int matchCountTotal) {

        String requestURL = setRequestURL(keyword);
        setJSONResponse(requestURL);
        setMatches(keyword, sources, matchCountTotal);

    }

    public List<Match> getMatches(){
        return this.matches;
    }

    public int getMatchCount(){
        return this.matchCount;
    }

    public Boolean isRepeatingSource(List<String> sources, String newSource){

        for(String src : sources){
            if (newSource.contains(src))
                return true;
        }

        return false;
    }

    public void showMatches(){
        for(Match m : matches){
            System.out.println("NAME: " + m.getName());
            System.out.println("URL: " + m.getSourceURL());
            System.out.println("INGREDIENTS: " + m.getIngredients());
        }
    }
}
