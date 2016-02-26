package com.emmanbraulio.final_thesis2.HomeActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.emmanbraulio.final_thesis2.Parsing.SearchRecipeActivity;
import com.emmanbraulio.final_thesis2.R;

public class search_food_recipe extends Activity {

    private static ImageView searchBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food_local);

        onClickSearch();
    }

    private void onClickSearch() {
        searchBtn = (ImageButton)findViewById(R.id.searchButton);
        searchBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(search_food_recipe.this, SearchRecipeActivity.class);

                        startActivity(intent);
                    }
                });
    }


}
