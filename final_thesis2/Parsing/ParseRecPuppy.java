package com.emmanbraulio.final_thesis2.Parsing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import okhttp3.HttpUrl;

public class ParseRecPuppy extends ParseSuper {

    @Override
    public final String setRequestURL(String keyword) {

        HttpUrl.Builder urlBuilder = HttpUrl.parse(
                "http://www.recipepuppy.com/api/?q=" +
                        keyword).newBuilder();

        String requestURL = urlBuilder.build().toString();
        System.out.println(requestURL);

        return requestURL;
    }

    @Override
    public final void setMatches(String keyword, List<String> sources, int matchCountTotal) {

        try {

            // Getting JSON Array node
            JSONArray matchesArr = this.JSONresponse.getJSONArray("results");

            if (matchesArr.length() != 0){

                for (int i = 0; i < matchesArr.length(); i++) {

                    if(matchCountTotal == 5)
                        break;

                JSONObject matchIndex = matchesArr.getJSONObject(i);

                    if(matchIndex.getString("title").toLowerCase().contains(keyword)) {

                        if (isUniqueSource(sources, matchIndex.getString("href"))) {

                            Match match = new Match();
                            match.setName(matchIndex.getString("title"));
                            match.setSourceURL(matchIndex.getString("href"));
                            match.setIngredients(Arrays.asList(matchIndex.getString("ingredients").split(",")));

                            this.matches.add(match);
                            this.matchCount++;
                            matchCountTotal++;

                        }

                    }
                }

                }

            else
                System.out.println("Recipe not found.");
        }

            catch (JSONException e) {
            e.printStackTrace();
        }

    }
}