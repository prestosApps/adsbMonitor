package com.prestos.adsbmonitor;

import com.prestos.adsbmonitor.model.Errors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by prestos on 22/10/2015.
 */
public class DataHandler {

    public static String getData(ApplicationURL applicationURL) throws ApplicationException {
        HttpURLConnection urlConnection;
        StringBuilder sb;

        try {
            urlConnection = (HttpURLConnection) applicationURL.openConnection();

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
            throw new ApplicationException("Unable to connect", Errors.CONNECTION_ERROR, ex);
        }
        return sb.toString();
    }

}
