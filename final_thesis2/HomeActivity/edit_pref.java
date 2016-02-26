package com.emmanbraulio.final_thesis2.HomeActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.emmanbraulio.final_thesis2.R;

public class edit_pref extends Activity {


    private static Button deleteFoodPref;
    ListView lv;
    EditText editText;
    DatabaseHelper myDB;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pref);

        lv = (ListView) findViewById(R.id.edit_pref);

        myDB = new DatabaseHelper(this);
        populateList();
        delete();
    }

    public void populateList()
    {
        Cursor cursor = myDB.getAllRows();
        startManagingCursor(cursor);

        String[] foodNames = new String[]{DatabaseHelper.col_name};
        int [] ID = new int[]{R.id.edit_pref_text};

        SimpleCursorAdapter myCursor = new SimpleCursorAdapter(this,R.layout.row_layout,cursor,foodNames,ID);
        ListView foodList = (ListView) findViewById(R.id.edit_pref);
        foodList.setAdapter(myCursor);

    }

    public void delete()
    {
        editText = (EditText)findViewById(R.id.food_delete);

        deleteFoodPref = (Button)findViewById(R.id.delete_food);
        deleteFoodPref.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isDeleted = myDB.deleteData(editText.getText().toString().trim().toLowerCase());
                        if (isDeleted == true) {
                            Toast.makeText(edit_pref.this, "Deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(edit_pref.this, "Not Deleted", Toast.LENGTH_SHORT).show();
                        }
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }

                });


    }

 }

