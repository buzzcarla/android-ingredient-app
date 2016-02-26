package com.emmanbraulio.final_thesis2.Parsing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ParseFork extends ParseSuper {

    String api_key = "86e8fea57103640282441b6f63267cba";

    public String getURL(String getRecipeURL){

        System.out.println("GET URL:" + getRecipeURL);

        Request request = new Request.Builder()
                .url(getRecipeURL)
                .build();

        OkHttpClient client = new OkHttpClient();

        try {
            try {
                Response response = client.newCall(request).execute();

                if (response != null) {

                    JSONObject jsonResponse = new JSONObject(response.body().string());
//                    return sourceURL;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String setGetRecipeURL(String recipeID){

        HttpUrl.Builder urlBuilder = HttpUrl.parse(
                "https://http://food2fork.com/api/get?key=" + api_key + "&rId=" + recipeID).newBuilder();

        String requestURL = urlBuilder.build().toString();
        return requestURL;
    }

    @Override
    public final String setRequestURL(String keyword) {

        HttpUrl.Builder urlBuilder = HttpUrl.parse("http://food2fork.com/api/search?key=" + api_key +
                "&q=" + keyword + "&sort=r").newBuilder();

        String requestURL = urlBuilder.build().toString();
        System.out.println(requestURL);

        return requestURL;
    }

    @Override
    public final void setMatches(String keyword, List<String> sources, int matchCountTotal) {

        try {

            JSONArray matchesArr = this.JSONresponse.getJSONArray("recipes");

            if (matchesArr.length() != 0) {

                int matchesFork = 0;

                for (int i = 0; (i < matchesArr.length()); i++) {

                    JSONObject matchIndex = matchesArr.getJSONObject(i);

                    if (isUniqueSource(sources, matchIndex.getString("source_url"))) {

                        if (matchIndex.getString("title").toLowerCase().contains(keyword)) {

                            String getRecipeURL = setGetRecipeURL(matchIndex.getString("recipe_id"));
//                            String sourceURL = getSourceURL(getRecipeURL);

                        }
                    }
                }

                System.out.println("matchesFork: " + matchesFork);
            } else
                System.out.println("Recipe not found.");

        } catch (JSONException e) {
            e.printStackTrace();
        }
            /*
                    JSONObject firstrecipe = recipes.getJSONObject(0);

                    String recipe_id = firstrecipe.getString("recipe_id");
                    ingredients.add("LABEL: " + title);

                    url = urlBuilder.build().toString();

                    request = new Request.Builder()
                            .url(url)
                            .build();
                    System.out.println(url);

                    response = client.newCall(request).execute();
                    jsonObj = new JSONObject(response.body().string());

                    firstrecipe = jsonObj.getJSONObject("recipe");
                    JSONArray ingredientLines = firstrecipe.getJSONArray("ingredients");

                    for (int i = 0; i < ingredientLines.length(); i++) {
                        ingredients.add(ingredientLines.get(i).toString());
                    }
            */
    }
}