package com.example.kelvs.parsing;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

import okhttp3.HttpUrl;


public class ParseEdamam extends ParseSuper {


            String id = "23086957";
            String key = "4f4bbe42ff481afdea4a0e7b29a19206";

            @Override
            public final String setRequestURL(String keyword) {

                HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.edamam.com/search?").newBuilder();
                urlBuilder.addQueryParameter("q", keyword);
                urlBuilder.addQueryParameter("app_id", id);
                urlBuilder.addQueryParameter("app_key", key);
                urlBuilder.addQueryParameter("to", "10");

                String requestURL = urlBuilder.build().toString();

                return requestURL;
            }

            @Override
            public final void setMatches(String keyword, List<String> sources, int matchCountTotal) {

                    try {

                        // Getting JSON Array node
                        JSONArray matchesArr = this.JSONresponse.getJSONArray("hits");

                        if (matchesArr != null) {

                            for (int i = 0; i < matchesArr.length(); i++) {

                                JSONObject jsonObject = matchesArr.getJSONObject(i);
                                JSONObject matchIndex = jsonObject.getJSONObject("recipe");

                                if(matchIndex.getString("label").toLowerCase().contains(keyword)) {

                                    if (!isRepeatingSource(sources, matchIndex.getString("url"))) {

                                        Match match = new Match();

                                        match.setName(matchIndex.getString("label"));
                                        match.setSourceURL(matchIndex.getString("url"));

                                        JSONArray ingredientLines = matchIndex.getJSONArray("ingredientLines");

                                        for (int j = 0; j < ingredientLines.length(); j++) {
                                            match.addIngredients(ingredientLines.get(j).toString());
                                        }

                                        this.matchCount++;
                                        this.matches.add(match);

                                    }
                                }
                            }
                        }
                        else
                            System.out.println("Recipe not found.");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
        }
}