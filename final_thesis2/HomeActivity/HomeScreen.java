package com.emmanbraulio.final_thesis2.HomeActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.emmanbraulio.final_thesis2.Parsing.SearchRecipeActivity;
import com.emmanbraulio.final_thesis2.R;
import com.emmanbraulio.final_thesis2.cameraOperation.Camera.CameraManager;

public class HomeScreen extends Activity {
    private static ImageButton searchImageButton;
    private static ImageButton camButton;
    private static Button editpreButton;
    private static Button addFoodButton;
    private static ImageButton about;
    private static TextView aboutText;
    private static Button display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        OnClickSearButton();
        onClickCamButton();
        onClickEditPref();
        onClickAddFood();
        onClickAbout();


    }
    public void OnClickSearButton()
    {
        searchImageButton = (ImageButton)findViewById(R.id.searchButton);
        searchImageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HomeScreen.this, SearchRecipeActivity.class);
                        startActivity(intent);
                    }
                });
    }

/*    public void onClickDisplay()
    {
        display = (Button) findViewById(R.id.displayButton);
        display.setOnClickListener(
                new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(HomeScreen.this,Display.class);
                        startActivity(intent);
                    }
                }
        );
    }*/

    public void onClickAbout()
    {
        aboutText = (TextView) findViewById(R.id.about_text);
        aboutText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                build();
                return false;
            }
        });


    }

    public void build()
    {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("About");
        builder.setMessage(R.string.about_description);
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.show();
    }

    public void  onClickAddFood()
    {
        addFoodButton = (Button) findViewById(R.id.addFood);
        addFoodButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HomeScreen.this, add_food.class);
                        startActivity(intent);
                    }
                }
        );
    }


    public void onClickEditPref()
    {
        editpreButton = (Button) findViewById(R.id.editPref);
        editpreButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HomeScreen.this, edit_pref.class);
                        startActivity(intent);
                    }
                }
        );
    }


    public void onClickCamButton()
    {
        camButton = (ImageButton) findViewById(R.id.camButton);
        camButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HomeScreen.this, CameraManager.class);
                        startActivity(intent);
                    }
                }
        );
    }


}

