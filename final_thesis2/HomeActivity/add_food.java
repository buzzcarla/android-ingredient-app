package com.emmanbraulio.final_thesis2.HomeActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.emmanbraulio.final_thesis2.R;


public class add_food extends Activity {
    private static Button addFoodPref;
    String food;
    boolean flag =false ,flag2=false;
    private AutoCompleteTextView foodName;

    final String[] allergies={"Chicken","Milk","Egg","Peanut","Wheat"
            ,"Soy","Fish","Crab","Shrimp","Lobster","Clam","Scallop"
            ,"Shellfish","Chili","Pepper","Pork","Beef","Onion"
            ,"Liver","Okra","Ampalaya","Fish","Brocolli"};
    DatabaseHelper myDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        myDB = new DatabaseHelper(this);


        onClickAddFood();
    }


    public void onClickAddFood()
    {
        foodName = (AutoCompleteTextView)findViewById(R.id.add_editText);
        food = foodName.getText().toString();
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice,allergies );
        foodName.setThreshold(1);
        foodName.setAdapter(adapter);


        addFoodPref = (Button) findViewById(R.id.add_foodButton);
        addFoodPref.setOnClickListener
                (


                         new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {



                                if (foodName.getText().toString().matches("")||myDB.CheckIsDataAlreadyInDBorNot(foodName.getText().toString().trim().toLowerCase())) {
                                    Toast.makeText(add_food.this, "Invalid Input", Toast.LENGTH_SHORT).show();
                                    foodName.setText("");
                                    return;
                                }

                                /*FEB 5*/
                                else  {
                                    boolean isInserted = myDB.insertData(foodName.getText().toString().trim().toLowerCase());

                                    if (isInserted == true) {
                                        Toast.makeText(add_food.this, "Added", Toast.LENGTH_SHORT).show();
                                        foodName.setText("");
                                        return;

                                    } else {
                                        Toast.makeText(add_food.this, "Not Added", Toast.LENGTH_SHORT).show();
                                        foodName.setText("");



                                                }
                                    flag=false;
                                    flag2=false;
                                            }


                                /*END*/

                            }

                         }
                );



    }
}

