package com.prestos.adsbmonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by prestos on 22/10/2015.
 */
public class DataHandler {

    public static String getData(String uri) throws IOException {
        URL url = null;
        HttpURLConnection urlConnection = null;
        StringBuilder sb = null;

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
        return sb.toString();
    }

}
