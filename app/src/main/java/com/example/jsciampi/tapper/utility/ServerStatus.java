package com.example.jsciampi.tapper.utility;

import android.os.AsyncTask;

import java.net.URL;
import java.net.URLConnection;

public class ServerStatus extends AsyncTask<Void, Void, Boolean>{
    public AsyncResponse delegate = null;

    @Override
    protected Boolean doInBackground(Void... voids) {
        try{
            URL myUrl = new URL("http://www.jacoposciampi.it");
            URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(5000);
            connection.connect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        delegate.processFinished(result);
    }
}
