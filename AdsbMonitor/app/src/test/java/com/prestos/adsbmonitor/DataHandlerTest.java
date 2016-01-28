package com.prestos.adsbmonitor;

import com.prestos.adsbmonitor.model.Errors;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import static org.mockito.Mockito.when;

/**
 * Created by prestos on 24/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class DataHandlerTest {

    private String data = "this is a test";
    private InputStream is;
    private String response;

    @Mock
    ApplicationURL applicationURL;

    @Mock
    ApplicationURL exceptionApplicationURL;

    @Mock
    HttpURLConnection httpURLConnection;

    @Mock
    HttpURLConnection exceptionHttpURLConnection;

    @Before
    public void setUp() {
        is = new ByteArrayInputStream(data.getBytes());
        try {
            when(applicationURL.openConnection()).thenReturn(httpURLConnection);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        try {
            when(exceptionApplicationURL.openConnection()).thenReturn(exceptionHttpURLConnection);
        } catch (ApplicationException e) {
            System.out.println("shit");
            e.printStackTrace();
        }
        try {
            when(httpURLConnection.getInputStream()).thenReturn(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            when(exceptionHttpURLConnection.getInputStream()).thenThrow(new IOException());
        } catch (IOException e) {
            System.out.println("shit1");
            e.printStackTrace();
        }
    }

    @Test
    public void dataHandler_success() {
        try {
            response = DataHandler.getData(applicationURL);
        } catch (ApplicationException e) {
            e.printStackTrace();
            Assert.fail("test should not fail");
        }
        Assert.assertEquals(data, response);
    }

    @Test
    public void dataHandler_exception() {
        try {
            response = DataHandler.getData(exceptionApplicationURL);
            Assert.fail("Test should have failed");
        } catch (ApplicationException e) {
            Assert.assertEquals("Unable to connect", e.getUserMessage());
            Assert.assertEquals(Errors.CONNECTION_ERROR, e.getError());
        }
    }

}
