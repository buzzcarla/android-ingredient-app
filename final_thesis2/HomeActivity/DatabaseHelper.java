package com.emmanbraulio.final_thesis2.HomeActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Derek on 2/1/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String dbName = "foodDB";
    public static final String table_name="food_table";
    public static final String incCol="_id";
    public static final String col_name="food_column";
    public static  final String[] ALL_KEYS = new String[]{incCol,col_name};

    public DatabaseHelper(Context context) {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + table_name + "(" + incCol + " INTEGER PRIMARY KEY AUTOINCREMENT, " + col_name + " FOOD TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + table_name);
            onCreate(db);
    }

    public boolean insertData(String food)
    {

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_name, food);
        long result = db.insert(table_name, null , contentValues);
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }


    String foodList;
    public String getAllData()
    {

        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + table_name, null);
        while (res.moveToNext())
        {
            foodList = res.getString(res.getColumnIndex(col_name));
            if (res != null) {
                res.moveToFirst();
            }
        }
        return foodList;
    }

    public Cursor getAllRows() {
        SQLiteDatabase db=this.getWritableDatabase();
        String where = null;
        Cursor c = db.query(true, table_name, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public boolean deleteData(String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(table_name, col_name+ " = "+"'"+name+"'",null) > 0;

    }

    public boolean CheckIsDataAlreadyInDBorNot(String fieldValue) {
        SQLiteDatabase myDB = this.getReadableDatabase();
        String Query = "Select * from " + table_name + " where " + col_name + " = '" + fieldValue + "' ";
        Cursor cursor = myDB.rawQuery(Query, null);
        cursor.moveToFirst();
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


}
