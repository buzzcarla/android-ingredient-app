package com.emmanbraulio.final_thesis2.imgProcess;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.emmanbraulio.final_thesis2.Parsing.ShowIngredientsActivity;
import com.emmanbraulio.final_thesis2.R;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* The main activity. Handles all I/O.
*/

public class ImgProcessManager extends Activity {

    private static final String TAG = "";
    private Mat mGray, mGray2;
    private Mat mEdgeDetect;
    private ProgressDialog progressDialog;

    Intent intent;
    public final static String EXTRA_MESSAGE = "com.example.kelvs.parsing";

    private String[] processResult = new String[3];
    /*Initialize OpenCV*/
    static {
        if(!OpenCVLoader.initDebug()) {
            Log.i("opencv", "opencv initialization   failed");
        }
        else {
            Log.i("opencv", "opencv initialization successful");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Load Tesseract data to phone storage*/
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

        //bp = (Bitmap) extras.get("data");
        /**USE SAMPLE IMAGE INSTEAD/
         *
         */
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Intent intent = getIntent();
        final Bitmap bp1;
        final Bitmap bp2;
        final Bitmap bp3;
        int count = intent.getIntExtra("InputIMG",0);
        switch(count){
            case 1: bp1 = (Bitmap) intent.getParcelableExtra("BitmapImage1");
                    processResult[0] = startProcess(bp1);
                    break;
            case 2: bp1 = (Bitmap) intent.getParcelableExtra("BitmapImage1");
                    bp2 = (Bitmap) intent.getParcelableExtra("BitmapImage2");
                    processResult[0] = startProcess(bp1);
                    processResult[1] = startProcess(bp2);
                    break;
            case 3: bp1 = (Bitmap) intent.getParcelableExtra("BitmapImage1");
                    bp2 = (Bitmap) intent.getParcelableExtra("BitmapImage2");
                    bp3 = (Bitmap) intent.getParcelableExtra("BitmapImage3");
                    processResult[0] = startProcess(bp1);
                    processResult[1] = startProcess(bp2);
                    processResult[2] = startProcess(bp3);
                    break;
            default: Log.e("BITMAP COUNT", "ERROR");
        }

        TextView my_out_1 = (TextView) findViewById(R.id.my_out);
        my_out_1.setText(processResult[0] + "\n" + processResult[1] + "\n" + processResult[2]);

        //Bitmap bp = BitmapFactory.decodeFile(PhotoHandler.filename, options);
        //Bitmap bp = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().toString()+"/tessdata/book2.jpg", options);
        /****************************OPEN BITMAP FROM OTHER ACTIVITY******************************************************************/
        //File file = PhotoHandler.getImgFile();
        //Bitmap bp = PhotoHandler.setImage2(file);
        /*File file = PhotoHandler.getImgFile();
        bp = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        bp = Bitmap.createScaledBitmap(bp, bp.getWidth(), bp.getHeight(), true);*/
        //Log.v(TAG, text);
        String lines[] = processResult[0].split("\\r?\\n");

        /*SEND TO PARSING*/
        intent = new Intent(this, ShowIngredientsActivity.class);
        for(int x = 0; x < lines.length ; x++){
            Log.v("RECIPE", lines[x]);
            intent.putExtra(EXTRA_MESSAGE, lines[x]);
            startActivity(intent);
        }
    }

    private String startProcess(Bitmap bp)
    {
        Mat toBinarize = new Mat();
        TessProcess startOCR = new TessProcess();
        InitTess initOCR = new InitTess();
        String txt = null;
        openCVProcess startOpenCV = new openCVProcess();
        if(bp != null) {
            Log.w("BITMAP:","Not Null");
            //iv = (ImageView) this.findViewById(R.id.imageView1);
            //iv.setImageBitmap(bp);
            //saveToFone(bp,"originalIMG.jpg");
        /*Cabs Code*/
            //convertedImg = new Mat();
        /*remove the comment*/

            mGray = startOpenCV.toGrayscale(bp);
            mGray2 = startOpenCV.gaussianBlur(mGray);
            mEdgeDetect = startOpenCV.edgeDetection(mGray);
            Core.addWeighted(mGray2, 0.5, mEdgeDetect, 0.5, 0, toBinarize);
            mGray = startOpenCV.binarize(mGray2);
            Utils.matToBitmap(mGray, bp);
            ImageView iv2 = (ImageView) findViewById(R.id.imageView1);
            iv2.setImageBitmap(bp);
            /*coment ends here*/
            txt = startOCR.tessProcess(bp);
            /**Save to Fone**/
            saveToFone(bp, "binaryIMG.jpg");
        }
        else
            Log.w("BITMAP:","Null");

        return txt;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String name = "trial";;
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                name,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        //mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    protected void saveToFone(Bitmap img, String filename)
    {
        String path = Environment.getExternalStorageDirectory().toString();
        OutputStream fOut = null;
        File file = new File(path, filename); // the File to save to
        try {
            fOut = new FileOutputStream(file);
            img.compress(Bitmap.CompressFormat.JPEG, 85, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            fOut.flush();
            fOut.close(); // do not forget to close the stream
            MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
            Log.w(TAG,"Image SAVED");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
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
