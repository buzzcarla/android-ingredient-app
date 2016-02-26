package com.emmanbraulio.final_thesis2.Parsing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.emmanbraulio.final_thesis2.R;


public class SearchRecipeActivity extends Activity {



    public final static String EXTRA_MESSAGE = "com.example.kelvs.parsing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe);
    }


    public void sendKeyWord (View view){

        Intent intent = new Intent(this, ShowIngredientsActivity.class);
        EditText editText = (EditText) findViewById(R.id.inputText);
        String keyword = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, keyword);
        startActivity(intent);
    }
}

