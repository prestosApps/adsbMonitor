package com.prestos.adsbmonitor.components;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.prestos.adsbmonitor.R;
import com.prestos.adsbmonitor.model.Receiver;

/**
 * Created by prestos on 18/01/16.
 */
public class IpCheckFragment extends Fragment {

    private TextView status;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ip_check, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        status = (TextView) getActivity().findViewById(R.id.fragment_ipcheck_status);
    }

    public void checkIpAddress(String ipAddress) {
        Log.d(IpCheckFragment.class.getName(), "Fragment checking " + ipAddress);
        status.setText("Checking " + ipAddress);
        new ReceiverDataLoader().execute(ipAddress);
    }

    private class ReceiverDataLoader extends AsyncTask<String, Void, Receiver> {

        private String URI = "/dump1090/data/receiver.json";

        @Override
        protected Receiver doInBackground(String... voids) {
            String receiverUrl = "http://" + voids[0] + URI;
            Receiver receiver = null;
//            try {
//                receiver = new Receiver(DataHandler.getData(receiverUrl));
//            } catch (IOException e) {
//                Log.e(IpCheckFragment.ReceiverDataLoader.class.getName(), "AAaarrggh!!", e);
//            }
            return receiver;
        }

        @Override
        protected void onPostExecute(Receiver receiver) {
            super.onPostExecute(receiver);
            status.setText("Found " + receiver.getVersion() + " version of Dump1090, good to go!");
        }
    }
}
