package com.emmanbraulio.final_thesis2.cameraOperation.Camera;

import android.content.Context;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by Emman Braulio on 2/16/2016.
 */

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "CameraPreview";

    private Camera mCamera;
    private SurfaceHolder mHolder;
    private byte[] mBuffer;

    int width, height;

    private CameraConfigurations cameraConfigurations;


    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;
        cameraConfigurations = new CameraConfigurations(context);

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    private void updateBufferSize() {
        mBuffer = null;
        System.gc();
        // prepare a buffer for copying preview data to
        int h = mCamera.getParameters().getPreviewSize().height;
        int w = mCamera.getParameters().getPreviewSize().width;
        int bitsPerPixel = ImageFormat.getBitsPerPixel(mCamera.getParameters().getPreviewFormat());
        mBuffer = new byte[w * h * bitsPerPixel / 8];
        //Log.i(TAG, "buffer length is " + mBuffer.length + " bytes");
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            //mCamera.setDisplayOrientation(90);    //for portrait view
            mCamera.setPreviewDisplay(surfaceHolder);
            updateBufferSize();
            mCamera.addCallbackBuffer(mBuffer);
            mCamera.setPreviewCallbackWithBuffer(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] bytes, Camera camera) {
                    if (mCamera != null) {
                        mCamera.addCallbackBuffer(mBuffer);
                    }
                }
            });
            mCamera.startPreview();

        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.

        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        cameraConfigurations.initFromCamera(mCamera);
        cameraConfigurations.setDesiredCameraParameters(mCamera);

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        // empty. Take care of releasing the Camera preview in your activity.
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }
}
