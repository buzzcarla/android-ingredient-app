package com.emmanbraulio.final_thesis2.imgProcess;

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

    public Mat gaussianBlur(Mat convertedIg) {
        //apply gaussian blur
        Size s = new Size(3, 3);
        Imgproc.GaussianBlur(convertedIg, mGray, s, 0, 0, Core.BORDER_DEFAULT);
        return mGray;
    }

    public Mat edgeDetection(Mat toEdgeDetect) {
        /*
        //apply Sobel edge detection

        //generate grad_x and grad_y
        Mat grad_x = new Mat();
        Mat convertedImg = new Mat(); //Edge Detect
        Mat grad_y = new Mat();
        Mat abs_grad_x = new Mat();
        Mat abs_grad_y = new Mat();
        int scale = 1;
        int delta = 0;
        int ddepth = CvType.CV_16S;

        //gradient x
        Imgproc.Sobel(toEdgeDetect, grad_x, ddepth, 1, 0, 3, scale, delta, Core.BORDER_DEFAULT);
        Core.convertScaleAbs(grad_x, abs_grad_x);

        //gradient y
        Imgproc.Sobel(toEdgeDetect, grad_y, ddepth, 0, 1, 3, scale, delta, Core.BORDER_DEFAULT);
        Core.convertScaleAbs(grad_y, abs_grad_y);

        //total gradient (approximate)
        Core.addWeighted(abs_grad_x, 0.5, abs_grad_y, 0.5, 0, convertedImg);
        return convertedImg;*/

        /*Canny Edge Detection*/
        Mat edge = new Mat();
        Imgproc.Canny(toEdgeDetect, edge,50, 150, 3);
        return edge;
    }

    public Mat binarize(Mat toBinarize) {
        Imgproc.adaptiveThreshold(mGray, mGray, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 11, 3);
        //Imgproc.threshold(toBinarize, mGray, 0, 255, Imgproc.THRESH_OTSU);
        //show bitmap in image view
        return mGray;
    }
}
