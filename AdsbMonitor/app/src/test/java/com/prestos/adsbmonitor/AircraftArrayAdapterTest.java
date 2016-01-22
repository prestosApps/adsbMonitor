package com.prestos.adsbmonitor;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;

import com.prestos.adsbmonitor.model.Aircraft;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by prestos on 22/01/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class AircraftArrayAdapterTest {

    @Mock
    private Activity aircraftActivity;
    @Mock
    private LayoutInflater mockLayoutInflater;
    @Mock
    private View responseView;

    private List<Aircraft> aircraftList;
    private AircraftArrayAdapter aircraftArrayAdapter;

    @Before
    public void setUp() {

        when(aircraftActivity.getLayoutInflater()).thenReturn(mockLayoutInflater);
        when(mockLayoutInflater.inflate(R.layout.aircraft_list_item, null)).thenReturn(responseView);

        aircraftList = new ArrayList<Aircraft>();
        aircraftArrayAdapter = new AircraftArrayAdapter(aircraftActivity, aircraftList);
    }

    @Test
    public void getView_success() {
        responseView = aircraftArrayAdapter.getView(0, null, null);
    }
}
