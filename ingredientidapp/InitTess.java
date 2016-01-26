package com.example.carlab.ingredientidapp;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This background function transfers tessdata from assets folder to the phone storage  
 * It only runs during the first launch of the app
 **/

final class InitTess extends MainActivity {

    private static final String TAG = "";

    public void copyAssets(Context ctx){
        Log.i(TAG, "assets loading");
        AssetManager assetManager = ctx.getAssets();
        String[] files = null;

        try {
            files = assetManager.list("");
            Log.i(TAG, "number" + files.length);
            for(int x = 0; x <= files.length-1 ; x++){
                Log.i(TAG, "File # " + x + 1 + "is: " + files[x]);
            }
        } catch (IOException e) {
            Log.e("tag", "Failed to get asset file list.", e);
        }

        File folder = new File(Environment.getExternalStorageDirectory().toString()+"/tessdata");
        folder.mkdirs();

        if (files != null) for (String filename : files) {
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(filename);
                File outFile = new File(Environment.getExternalStorageDirectory().toString()+"/tessdata", filename);
                out = new FileOutputStream(outFile);
                copyFile(in, out);
            } catch(IOException e) {
                Log.e("tag", "Failed to copy asset file: " + filename, e);
            }
            finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        // NOOP
                    }
                }
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

}
