package com.prestos.adsbmonitor.model;

import com.prestos.adsbmonitor.ApplicationException;
import com.prestos.adsbmonitor.BuildConfig;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by prestos on 25/01/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class ReceiverTest {

    private static final String SUCCESS = "{ \"version\" : \"v1.15~dev\", \"refresh\" : 1000, \"history\" : 120, \"lat\" : 12.34, \"lon\" : 1.23 }";

    private Receiver receiver;

    @Before
    public void setUp() {
        try {
            receiver = new Receiver(SUCCESS);
        } catch (ApplicationException e) {
            Assert.fail("Test should have passed");
        }
    }

    @Test
    public void receiver_success() {
        Assert.assertNotNull(receiver);
    }

    @Test
    public void receiver_version() {
        Assert.assertEquals("v1.15~dev", receiver.getVersion());
    }

    @Test
    public void receiver_refresh() {
        Assert.assertEquals(1000, receiver.getRefresh());
    }

    @Test
    public void receiver_history() {
        Assert.assertEquals(120, receiver.getHistory());
    }

    @Test
    public void receiver_lat() {
        Assert.assertEquals(12.34d, receiver.getLat());
    }

    @Test
    public void receiver_lon() {
        Assert.assertEquals(1.23d, receiver.getLon());
    }
}
