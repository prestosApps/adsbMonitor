package com.prestos.adsbmonitor;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by prestos on 22/10/2015.
 */
public class DataHandler {

    public static String getData(String uri) throws ApplicationException {
        URL url = null;
        HttpURLConnection urlConnection = null;
        StringBuilder sb = null;

        try {
            url = new URL(uri);
            urlConnection = (HttpURLConnection) url.openConnection();

            sb = new StringBuilder();
            InputStreamReader isr = new InputStreamReader(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            String output = br.readLine();
            while (output != null) {
                sb.append(output);
                output = br.readLine();
            }
            br.close();
            isr.close();
            urlConnection.disconnect();
        } catch (IOException ex) {
            Log.e(DataHandler.class.getName(), "Error occurred whilst trying to connect to " + uri, ex);
            throw new ApplicationException("Unable to connect", Errors.CONNECTION_ERROR, ex);
        }
        return sb.toString();
    }

}
