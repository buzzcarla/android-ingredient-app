package com.example.carlab.ingredientidapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    private Mat mGray;
    //private Mat convertedImg;
    private Bitmap bp;
    private Bitmap bmpMonochrome;

    static {
        if(!OpenCVLoader.initDebug()) {
            Log.i("opencv", "opencv initialization failed");
        }
        else {
            Log.i("opencv", "opencv initialization successful");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = (Button) this.findViewById(R.id.camButton);

        SharedPreferences settings = getSharedPreferences("MyPrefs", 0); // Get preferences file (0 = no option flags set)
        boolean firstRun = settings.getBoolean("firstRun", true);

        if (firstRun) {
            Log.w("activity", "first time");
            InitTess initOCR = new InitTess();
            initOCR.copyAssets(this);
            SharedPreferences.Editor editor = settings.edit(); // Open the editor for our settings
            editor.putBoolean("firstRun", false); // It is no longer the first run
            editor.commit(); // Save all changed settings
        } else {
            Log.w("activity", "second time");
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        TessProcess startOCR = new TessProcess();
        //Did the user choose OK? If so, tne code below will execute
        //if (resultCode == RESULT_OK) {
        if (requestCode == 0) {
            //Bitmap bp = (Bitmap) data.getExtras().get("data");
            Bundle extras = data.getExtras();
            bp = (Bitmap) extras.get("data");
            //iv = (ImageView) this.findViewById(R.id.imageView1);
            //iv.setImageBitmap(bp);
            /*Cabs Code*/
            //convertedImg = new Mat();
            toGrayscale();
            gaussianBlur();
            binarize();
            String txt = startOCR.tessProcess(bp);
            TextView my_out_1 = (TextView)findViewById(R.id.my_out);
            my_out_1.setText(txt);
        }

        //pang debug ra ni (machange "Hello" ang text if dili muwork ang camera)
        else {
            TextView txt = (TextView) this.findViewById(R.id.textview);
            txt.setText("Hello");
        }
    }

    public void toGrayscale() {
        //convert to grayscale
        //mGray = new Mat (myImg.rows(), myImg.cols(), CvType.CV_8UC1, new Scalar(0));
        mGray = new Mat(bp.getWidth(), bp.getHeight(), CvType.CV_8UC1);
        Utils.bitmapToMat(bp, mGray);
        Imgproc.cvtColor(mGray, mGray, Imgproc.COLOR_RGB2GRAY);
    }

    public void gaussianBlur() {
        //apply gaussian blur
        org.opencv.core.Size s = new Size(3,3);
        Imgproc.GaussianBlur(mGray, mGray, s, 0, 0, Core.BORDER_DEFAULT);
    }

    public void binarize() {
        //Imgproc.adaptiveThreshold(mGray, mGray, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 5, 4);
        Imgproc.threshold(mGray, mGray, 0, 255, Imgproc.THRESH_OTSU);

        //show bitmap in image view
        Utils.matToBitmap(mGray, bp);
        ImageView iv2= (ImageView)findViewById(R.id.imageView1);
        iv2.setImageBitmap(bp);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
