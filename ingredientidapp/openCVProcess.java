package com.example.carlab.ingredientidapp;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * This background function processes image taken from the camera.
 * It returns a processed Bitmap.
 **/

final class openCVProcess {

    private Mat mGray;

    public Mat toGrayscale(Bitmap bp1) {
        //convert to grayscale
        //mGray = new Mat (myImg.rows(), myImg.cols(), CvType.CV_8UC1, new Scalar(0));
        mGray = new Mat(bp1.getWidth(), bp1.getHeight(), CvType.CV_8UC1);
        Utils.bitmapToMat(bp1, mGray);
        Imgproc.cvtColor(mGray, mGray, Imgproc.COLOR_RGB2GRAY);
        return mGray;
    }

    public Mat gaussianBlur(Mat convertedImg) {
        //apply gaussian blur
        org.opencv.core.Size s = new Size(3, 3);
        Imgproc.GaussianBlur(convertedImg, mGray, s, 0, 0, Core.BORDER_DEFAULT);
        return mGray;
    }

    public Mat binarize(Mat toBinarize) {
        //Imgproc.adaptiveThreshold(mGray, mGray, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 5, 4);
        Imgproc.threshold(toBinarize, mGray, 0, 255, Imgproc.THRESH_OTSU);
        //show bitmap in image view
        return mGray;
    }
}
