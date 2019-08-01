package com.example.vibhor.downloadimageswithasyntask;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by vibhor on 17-Jun-16.
 */

public class MyTask extends AsyncTask<String, Integer, Boolean> {
    private int contentLength = -1;
    private int counter = 0;
    private int calculatedProgress = 0;
    private Activity activity;
    private Context context;

    public MyTask(Context context) {
        onAttach(context);

    }

    public void onAttach(Context context) {
        this.context = context;
    }


    public void onDetach() {
        context = null;
    }

    @Override
    protected void onPreExecute() {

         /*   if(MainActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            {
                MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            }else{

                MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }*/

        if(context !=null)
        {
            ((MainActivity)context).showProgressBarBeforeDownloading();
        }


    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Boolean doInBackground(String... params) {
        boolean success = false;
        HttpURLConnection httpURLConnection = null;
        URL downloadUrl = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;

        File file = null;
        try {

            /**Here http Connection have
             * been made
             */
            downloadUrl = new URL(params[0]);
            httpURLConnection = (HttpURLConnection) downloadUrl.openConnection();
            contentLength = httpURLConnection.getContentLength();


            /**Here  data have been stored to the
             * external storage card
             */
            inputStream = httpURLConnection.getInputStream();

            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .getAbsolutePath() + "/" + Uri.parse(params[0]).getLastPathSegment());
            L.m(file.getAbsolutePath());


            fileOutputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1024];

            int read = -1;
            while ((read = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, read);
                counter = counter + read;
                publishProgress(counter);
            }
            success = true;
        } catch (MalformedURLException e) {

        } catch (IOException e) {

        } finally {


            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {

                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {

                }
            }
        }
        return success;

    }

    @Override
    protected void onProgressUpdate(Integer... values) {


        if(context == null)
        {
            L.m("Skip this step");
        }else
        {
            ((MainActivity)context).updateProgress(calculatedProgress);
            calculatedProgress = (int) (((double) values[0] / contentLength) * 100);

        }

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {

        if(context != null) {
            ((MainActivity) context).hideProgressBarAfterDownloading();
            // MainActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        }
    }
}