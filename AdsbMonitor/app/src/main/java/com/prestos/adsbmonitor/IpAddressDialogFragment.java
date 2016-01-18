package com.prestos.adsbmonitor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by prestos on 18/01/2016.
 */
public class IpAddressDialogFragment extends DialogFragment {

    public interface IpAddressDialogListener {
        public void onDialogClick(String ipAddress);
    }

    private IpAddressDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (IpAddressDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString() + " must implement IpAddressDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View customView = inflater.inflate(R.layout.dialog_ip_address, null);

        builder.setMessage("IP Address");
        builder.setView(customView);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText ipText = (EditText) customView.findViewById(R.id.dialog_ip_address_text);
                mListener.onDialogClick(ipText.getText().toString());
            }
        });

        return builder.create();
    }
}
