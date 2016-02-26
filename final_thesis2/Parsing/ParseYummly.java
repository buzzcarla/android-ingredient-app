package com.emmanbraulio.final_thesis2.Parsing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.HttpUrl;

public class ParseYummly extends ParseSuper {

    // DECLARING ID AND KEY

    String app_id = "f91a37f4";
    String api_key = "8d623bbb90e252baa99619a82a96e659";

    // METHOD DECLARATIONS

    @Override
    public final String setRequestURL(String keyword) {

        HttpUrl.Builder urlBuilder = HttpUrl.parse(
                "https://api.yummly.com/v1/api/recipes?" +
                        "_app_id=" + this.app_id).newBuilder();

        urlBuilder.addQueryParameter("_app_key", this.api_key);
        urlBuilder.addQueryParameter("q", keyword);

        //set # of results
        urlBuilder.addQueryParameter("maxResult", "10");

        return urlBuilder.build().toString();

    }

    /* JSON PARSING */

    @Override
    public final void setMatches(String keyword, List<String> sources, int matchCountTotal) {

        try {
            // Getting JSON Array node
            JSONArray matchesArr = this.JSONresponse.getJSONArray("matches");

            // if matches are found
            if (matchesArr != null) {

                for(int i = 0; i < matchesArr.length(); i++) {

                    if(matchCountTotal == 5)
                        break;

                    JSONObject matchIndex = matchesArr.getJSONObject(i);

                    // get 3 vars from matchIndex

                    // filtering recipeNames
                    if(matchIndex.getString("recipeName").toLowerCase().contains(keyword)) {

                        Match match = new Match();
                        match.setName(matchIndex.getString("recipeName"));
                        match.setSourceURL(matchIndex.getString("sourceDisplayName"));

                        JSONArray ingredientLines = matchIndex.getJSONArray("ingredients");

                        //looping through ingredients
                        for (int j = 0; j < ingredientLines.length(); j++)
                            match.addIngredients(ingredientLines.get(j).toString());

                        this.matchCount++;
                        this.matches.add(match);

                        matchCountTotal++;
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
