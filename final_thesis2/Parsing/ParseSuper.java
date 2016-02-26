package com.emmanbraulio.final_thesis2.Parsing;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class ParseSuper {

    // ATTRIBUTE DECLARATIONS

    public JSONObject JSONresponse = null;
    public List<Match> matches = new ArrayList<>();
    public int matchCount = 0;


    // METHOD DECLARATIONS -----------------------------------------------

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

        setJSONResponse(setRequestURL(keyword));
        setMatches(keyword, sources, matchCountTotal);

    }

    public Boolean isUniqueSource(List<String> sources, String newSource){
        for(String src : sources){
            if(src.contains(newSource))
                return false;
        }

        return true;
    }

    public List<Match> getMatches(){
        return this.matches;
    }

    public int getMatchCount(){
        return this.matchCount;
    }

    public void showMatches(){
        for(Match m : matches){
            System.out.println("NAME: " + m.getName());
            System.out.println("URL: " + m.getSourceURL());
            System.out.println("INGREDIENTS: " + m.getIngredients());
        }
    }
}
