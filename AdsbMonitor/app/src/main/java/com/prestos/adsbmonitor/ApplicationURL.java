package com.prestos.adsbmonitor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by prestos on 24/01/2016.
 */
public class ApplicationURL {

    private URL url;

    public ApplicationURL(String url) throws MalformedURLException {
        this.url = new URL(url);
    }

    public URLConnection openConnection() throws IOException {
        return url.openConnection();
    }
}
