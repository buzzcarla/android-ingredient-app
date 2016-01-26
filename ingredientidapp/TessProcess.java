package com.example.carlab.ingredientidapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;

import com.googlecode.tesseract.android.TessBaseAPI;

/**
 * This is the the background OCR Process
 * Accepts Bitmap from the openCV processed image and returns the read text
 **/

final class TessProcess extends Activity{

	    protected String tessProcess(Bitmap bMap){
        TessBaseAPI tessapi = new TessBaseAPI();
        //tessapi.init("/storage/sdcard0/", "eng");
        tessapi.init(Environment.getExternalStorageDirectory().toString(), "eng");
        //ImageView image = (ImageView)findViewById(R.id.test_image);
        //Bitmap bMap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().toString() + "/tessdata/sample_input.bmp");
        //image.setImageBitmap(bMap);
        tessapi.setImage(bMap);
        String txt = tessapi.getUTF8Text();
        tessapi.end();
        //Log.i(TAG, "OCR output:\n" + txt);
        //TextView my_out_1 = (TextView)findViewById(R.id.my_out);
        //my_out_1.setText("Hello:" + txt);
        return txt;
    }
}
