package com.emmanbraulio.final_thesis2.HomeActivity;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.emmanbraulio.final_thesis2.R;

import java.util.ArrayList;
import java.util.Arrays;

public class Display extends Activity {

    DatabaseHelper myDB;
    ListView lv;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        myDB= new DatabaseHelper(this);
        check();
    }

    public void check()
    {

        ArrayAdapter arrayAdapter;
        Toast.makeText(Display.this, "HOIOTPAOPT", Toast.LENGTH_SHORT).show();
        lv = (ListView) findViewById(R.id.displayList);
        final  ArrayList<String> foodNot = new ArrayList<>();
        ArrayList<String > food = new ArrayList<>(
                Arrays.asList("pork", "large eggs", "bitter melon", "squash", "large tomato", "onions", "ginger", "garlic", "okra",
                        "string beans", "shrimp paste", "water", "cooking oil", "salt", "pepper"));
        Log.d("DEREK", "Size of array : " +food.size());
        int foodSize=food.size();
        SQLiteDatabase db=myDB.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + " food_table ", null);
        res.moveToFirst();


        for(int x=0; x < foodSize;x++)
        {
            /*Toast.makeText(Display.this, "WHAAT", Toast.LENGTH_SHORT).show();*/
            String foodRes = food.get(x);

            /*tv.setText("HI");*/
            String foodList;
            Log.d("test", "Size of array : " + food.size());

            if (res != null && res.moveToFirst());
            do {

                foodList = res.getString(res.getColumnIndex("food_column"));/*
                    Toast.makeText(Display.this, foodList, Toast.LENGTH_SHORT).show();*/
                Log.d("DerekLust", foodList);
                Log.d("GWAPA", foodRes);

                if (foodRes.contains(foodList)) {
//                    Toast.makeText(Display.this, "GISHE", Toast.LENGTH_SHORT).show();
                    foodNot.add(foodRes);
//                    Toast.makeText(Display.this, foodList, Toast.LENGTH_SHORT).show();


                }
                res.moveToNext();
            }while (res.moveToNext());
            arrayAdapter = new ArrayAdapter<>(this,R.layout.display_layout,R.id.displayFoodName,foodNot);
            lv.setAdapter(arrayAdapter);
        }


    }




}
