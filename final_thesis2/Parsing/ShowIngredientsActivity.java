package com.emmanbraulio.final_thesis2.Parsing;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.emmanbraulio.final_thesis2.R;


public class ShowIngredientsActivity extends Activity {

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_ingredients);

        BackgroundWorker bw = new BackgroundWorker(ShowIngredientsActivity.this);

        Intent intent = getIntent();
        bw.keyword = intent.getStringExtra(SearchRecipeActivity.EXTRA_MESSAGE);
        bw.execute();

//        ListView lv;
//        lv = (ListView) findViewById(R.id.listViewIngredients);
//
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
//                this,
//                android.R.layout.simple_list_item_1,
//                bw.returnIngredients());
//
//        System.out.println(bw.returnIngredients());
//
//        lv.setAdapter(arrayAdapter);
    }

}