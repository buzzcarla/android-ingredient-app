package com.emmanbraulio.final_thesis2.Parsing;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class BackgroundWorker extends AsyncTask<Void, Void, Void> {

    /*
        DECLARATIONS
    */

    String keyword;
    Activity mActivity;
    ProgressDialog pDialog;

    // CONSTRUCTOR

    public BackgroundWorker (Activity activity){
        super();
        mActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress dialog
        pDialog = new ProgressDialog(this.mActivity);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(true);
        pDialog.show();
    }

    /*
        DO IN BACKGROUND
     */

    @Override
    protected Void doInBackground(Void... arg0) {

        // sample list of user ingredients

        IngredCounter ingredCounter = new IngredCounter();
        ingredCounter.getAllMatches(keyword);
        ingredCounter.showCountedIngredients();

        return null;
    }

    @Override
    protected void onPostExecute (Void result){
        super.onPostExecute(result);

        // Dismiss the progress dialog
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}