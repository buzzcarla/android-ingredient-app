package com.emmanbraulio.final_thesis2.cameraOperation.Camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.emmanbraulio.final_thesis2.Parsing.BackgroundWorker;
import com.emmanbraulio.final_thesis2.R;
import com.emmanbraulio.final_thesis2.cameraOperation.ExtraViews.FocusBoxView;
import com.emmanbraulio.final_thesis2.cameraOperation.ImageHandling.PhotoHandler;
import com.emmanbraulio.final_thesis2.imgProcess.ImgProcessManager;

import java.util.ArrayList;


public class CameraManager extends Activity implements View.OnClickListener {

    private Camera mCamera;
    private CameraPreview mPreview;
    private FocusBoxView focusBoxView;
    private BackgroundWorker bw;

    private static final float FOCUS_AREA_SIZE = 75f;

    ImageView camBtn, focusBtn;
    View result_view;


    ArrayList<Bitmap> capturedImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_manager);

        //create an instance of camera, and other classes
        mCamera = getCameraInstance();
        mPreview = new CameraPreview(this, mCamera);
        capturedImages = new ArrayList<>();
        bw = new BackgroundWorker(this);

        //create an instance of our views and buttons
        focusBoxView = (FocusBoxView)findViewById(R.id.focus_box_view);
        camBtn = (ImageView)findViewById(R.id.btn_takePhoto);
        focusBtn = (ImageView) findViewById(R.id.btn_focus);
        //result_view = findViewById(R.id.result_view);

        // Create our Preview view
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        // Set onclickListeners
        camBtn.setOnClickListener(this);
        focusBtn.setOnClickListener(this);
    }

    @Override
      public void onClick(View view) {
        if(view == camBtn)
            mCamera.takePicture(shutterCallback, null, mPicture);

        if(view == focusBtn)
            mCamera.autoFocus(autoFocusCallback);
    }

    //a safe way to get an instance of a camera object
    public static Camera getCameraInstance() {
        Camera c = null;

        try {
            c = Camera.open();  //attempt to get a Camera instance
        }
        catch (Exception e){
            //Camera is not available (in use or does not exist)
            Log.d("Camera instance", "Error getting camera instance");
        }
        return c;
    }

    Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean success, Camera camera) {

        }
    };

    private Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        @Override
        public void onShutter() {
            /* empty shuttercallback, instead of a null in takePicture() method, plays a sound. wahihihihi */
        }
    };

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap croppedImage = PhotoHandler.getFocusedBitmap(getApplicationContext(), camera, data, focusBoxView.getBox());
            Log.d("Picture", "Bitmap: " + croppedImage.getWidth() + " " + croppedImage.getHeight());
            /*result_view.setVisibility(View.VISIBLE);
            focusBoxView.setVisibility(View.GONE);
            camBtn.setVisibility(View.GONE);
            ImageView iv = (ImageView) findViewById(R.id.captured_image);
            iv.setImageBitmap(croppedImage);*/
            Intent intent = new Intent(CameraManager.this, ImgProcessManager.class);
            intent.putExtra("BitmapCaptured", croppedImage);
            startActivity(intent);
            //mCamera.startPreview();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();    // release the camera immediately on pause event
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mCamera == null){
            mCamera = getCameraInstance();

            mPreview = new CameraPreview(this, mCamera);
            FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
            preview.addView(mPreview);
        }
    }

    private void releaseCamera(){
        if (mCamera != null){
            mPreview.getHolder().removeCallback(mPreview);
            mCamera.release();
            mCamera = null;
        }
    }
}
