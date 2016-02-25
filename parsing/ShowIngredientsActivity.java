package com.example.kelvs.parsing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ShowIngredientsActivity extends AppCompatActivity {

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