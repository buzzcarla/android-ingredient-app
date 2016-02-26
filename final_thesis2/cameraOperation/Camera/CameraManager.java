package com.emmanbraulio.final_thesis2.cameraOperation.Camera;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.emmanbraulio.final_thesis2.R;
import com.emmanbraulio.final_thesis2.cameraOperation.ExtraViews.FocusBoxView;
import com.emmanbraulio.final_thesis2.cameraOperation.ImageHandling.PhotoHandler;
import com.emmanbraulio.final_thesis2.cameraOperation.Listeners.OnDoubleTapListener;
import com.emmanbraulio.final_thesis2.imgProcess.ImgProcessManager;

import java.util.ArrayList;


public class CameraManager extends Activity implements View.OnClickListener {

    private Camera mCamera;
    private CameraPreview mPreview;
    private PhotoHandler photoHandler;
    private FocusBoxView focusBoxView;

    Button show_captures, doneBtn, backBtn;
    View resultView;
    ImageView img1,img2,img3, camBtn;
    TextView disp_img_cnt;

    final int duration = Toast.LENGTH_SHORT;

    ArrayList<Bitmap> capturedImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_manager);

        //create an instance of camera, and other classes
        mCamera = getCameraInstance();
        photoHandler = new PhotoHandler(this);
        mPreview = new CameraPreview(this, mCamera);
        capturedImages = new ArrayList<>();

        //create an instance of our views and buttons
        resultView = findViewById(R.id.result_view);
        focusBoxView = (FocusBoxView)findViewById(R.id.focus_box_view);
        show_captures = (Button) findViewById(R.id.show_capturedImages);
        backBtn = (Button) findViewById(R.id.back);
        doneBtn = (Button) findViewById(R.id.done);
        camBtn = (ImageView)findViewById(R.id.btn_takePhoto);
        img1 = (ImageView) findViewById(R.id.image1);
        img2 = (ImageView) findViewById(R.id.image2);
        img3 = (ImageView) findViewById(R.id.image3);
        disp_img_cnt = (TextView) findViewById(R.id.image_counter);

        // Create our Preview view
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        // Set onclickListeners
        camBtn.setOnClickListener(this);
        show_captures.setOnClickListener(this);
        backBtn.setOnClickListener(this);
        doneBtn.setOnClickListener(this);
        showImgCount();

        img1.setOnTouchListener(new OnDoubleTapListener(this) {
            @Override
            public void onDoubleTap(MotionEvent e) {
                deleteImg(0);
            }
        });
        img2.setOnTouchListener(new OnDoubleTapListener(this){
            @Override
            public void onDoubleTap(MotionEvent e) {
                deleteImg(1);
            }
        });
        img3.setOnTouchListener(new OnDoubleTapListener(this){
            @Override
            public void onDoubleTap(MotionEvent e) {
                deleteImg(2);
            }
        });
    }

    public void showImgs() {
        for(int x=0; x < capturedImages.size(); x++) {
            switch (x) {
                case 0:     img1.setImageBitmap(capturedImages.get(x)); break;
                case 1:     img2.setImageBitmap(capturedImages.get(x)); break;
                case 2:     img3.setImageBitmap(capturedImages.get(x)); break;
                default:    Toast.makeText(getApplicationContext(), "No images", duration).show();
            }
        }
    }

    public void showImgCount() {
        switch (capturedImages.size()) {
            case 1: disp_img_cnt.setText("2 shots remaining");  break;
            case 2: disp_img_cnt.setText("1 shot remaining");  break;
            case 3: disp_img_cnt.setText("Completed Shots");    break;
            default: disp_img_cnt.setText("3 shots remaining"); break;
        }
    }

    public void deleteImg(final int img_index) {
        AlertDialog.Builder showDelete = new AlertDialog.Builder(this);
        showDelete.setTitle("Delete image")
                .setMessage("Are you sure you want to delete this image?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //continue with deleting
                        capturedImages.get(img_index).recycle();
                        capturedImages.remove(img_index);

                        img1.setImageBitmap(null);
                        img2.setImageBitmap(null);
                        img3.setImageBitmap(null);

                        showImgCount();
                        disp_img_cnt.setVisibility(View.VISIBLE);
                        show_captures.setVisibility(View.VISIBLE);
                        camBtn.setVisibility(View.VISIBLE);
                        focusBoxView.setVisibility(View.VISIBLE);
                        resultView.setVisibility(View.GONE);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void onClick(View view) {
        if(view == camBtn) {
            if(capturedImages.size() < 3)
                takePhoto(view);
            else {
                CharSequence text = "You have reached your limit.";
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();
            }
        }

        if(view == show_captures) {
            if(capturedImages.isEmpty()) {
                CharSequence text = "No pictures were taken.";
                Toast toast = Toast.makeText(getApplicationContext(), text, duration);
                toast.show();
            }

            else {
                disp_img_cnt.setVisibility(View.GONE);
                show_captures.setVisibility(View.GONE);
                camBtn.setVisibility(View.GONE);
                focusBoxView.setVisibility(View.GONE);
                resultView.setVisibility(View.VISIBLE);
                showImgs();
            }
        }

        if(view == backBtn) {
            showImgCount();
            disp_img_cnt.setVisibility(View.VISIBLE);
            show_captures.setVisibility(View.VISIBLE);
            camBtn.setVisibility(View.VISIBLE);
            focusBoxView.setVisibility(View.VISIBLE);
            resultView.setVisibility(View.GONE);
        }

        if(view == doneBtn){
            Intent intent = new Intent(CameraManager.this, ImgProcessManager.class);
            switch (capturedImages.size()){
                case 1: intent.putExtra("BitmapImage1", capturedImages.get(0));
                        break;
                case 2: intent.putExtra("BitmapImage1", capturedImages.get(0));
                        intent.putExtra("BitmapImage2", capturedImages.get(1));
                        break;
                case 3: intent.putExtra("BitmapImage1", capturedImages.get(0));
                        intent.putExtra("BitmapImage2", capturedImages.get(1));
                        intent.putExtra("BitmapImage3", capturedImages.get(2));
                        break;
            }
            intent.putExtra("InputIMG", capturedImages.size());
            startActivity(intent);
        }
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

    public void takePhoto(View v) {
        mCamera.takePicture(null, null, mPicture);
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap croppedImage = photoHandler.getFocusedBitmap(getApplicationContext(), camera, data, focusBoxView.getBox());
            Log.d("Picture", "Bitmap: "+croppedImage.getWidth() +" "+croppedImage.getHeight());
            capturedImages.add(croppedImage);
            showImgCount();
            mCamera.startPreview();
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
