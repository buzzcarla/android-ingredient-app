package com.example.kelvs.parsing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;
import okhttp3.HttpUrl;


public class ParseYummly extends ParseSuper {

    String app_id = "f91a37f4";
    String api_key = "8d623bbb90e252baa99619a82a96e659";

    /*
    public String getSourceURL(String getRecipeURL){

        System.out.println("GET URL:" + getRecipeURL);
        Request request = new Request.Builder()
                .url(getRecipeURL)
                .build();

        OkHttpClient client = new OkHttpClient();

        try {
            Response response = client.newCall(request).execute();

            if (response != null) try {

                JSONObject jsonResponse = new JSONObject(response.body().string()).getJSONObject("source");

                String sourceURL = jsonResponse.getString("sourceRecipeUrl");
                System.out.println(sourceURL);

                return sourceURL;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public String setGetRecipeURL(String recipeID){

        HttpUrl.Builder urlBuilder = HttpUrl.parse(
                "https://api.yummly.com/v1/api/recipe/" + recipeID +
                        "?_app_id=" + this.app_id + "&_app_key=" + api_key).newBuilder();

        String requestURL = urlBuilder.build().toString();
        return requestURL;
    }
    */

    @Override
    public final String setRequestURL(String keyword) {

        HttpUrl.Builder urlBuilder = HttpUrl.parse(
                "https://api.yummly.com/v1/api/recipes?" +
                        "_app_id=" + this.app_id).newBuilder();

        urlBuilder.addQueryParameter("_app_key", this.api_key);
        urlBuilder.addQueryParameter("q", keyword);

        //set # of results
        urlBuilder.addQueryParameter("maxResult", "5");

        String requestURL = urlBuilder.build().toString();
        System.out.println(requestURL + "   1");

        return requestURL;
    }

    /* JSON PARSING */

    @Override
    public final void setMatches(String keyword, List<String> sources, int matchCountTotal) {

        try {
            // Getting JSON Array node
            JSONArray matchesArr = this.JSONresponse.getJSONArray("matches");

            // if matches are found
            if (matchesArr != null) {

                for(int i = 0; (i < matchesArr.length()); i++) {

                    JSONObject matchIndex = matchesArr.getJSONObject(i);

                    // get 3 vars from matchIndex

                    // filtering recipeNames
                    if(matchIndex.getString("recipeName").toLowerCase().contains(keyword)) {

                        /*
                        //set URL for GET Recipe
                        String getRecipeURL = setGetRecipeURL(matchIndex.getString("id"));
                        String sourceURL = getSourceURL(getRecipeURL);
                        */

                        Match match = new Match();
                        match.setName(matchIndex.getString("recipeName"));
                        match.setSourceURL(matchIndex.getString("sourceDisplayName"));

                        JSONArray ingredientLines = matchIndex.getJSONArray("ingredients");

                        //looping through ingredients
                        for (int j = 0; j < ingredientLines.length(); j++)
                            match.addIngredients(ingredientLines.get(j).toString());

                        this.matchCount++;
                        this.matches.add(match);
                    }
                }

                // if no matches
            } else
                System.out.println("RECIPE NOT FOUND");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
