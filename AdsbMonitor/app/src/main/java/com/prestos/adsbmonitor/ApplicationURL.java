package com.prestos.adsbmonitor;

import com.prestos.adsbmonitor.model.Errors;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by prestos on 24/01/2016.
 */
public class ApplicationURL {

    private URL url;

    public ApplicationURL(String url) throws ApplicationException {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new ApplicationException("cock", Errors.CONNECTION_ERROR, e);
        }
    }

    public URLConnection openConnection() throws ApplicationException {
        try {
            return url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApplicationException("cock", Errors.CONNECTION_ERROR, e);
        }
    }
}
