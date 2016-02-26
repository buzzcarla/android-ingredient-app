package com.emmanbraulio.final_thesis2.HomeActivity;

import android.app.Activity;
import android.os.Bundle;

import com.emmanbraulio.final_thesis2.R;

public class Option extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        onClickAbout();

    }

    public void onClickAbout(){}
}
