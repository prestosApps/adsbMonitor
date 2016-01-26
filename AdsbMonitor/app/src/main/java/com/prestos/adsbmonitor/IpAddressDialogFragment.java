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
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by prestos on 18/01/2016.
 */
public class IpAddressDialogFragment extends DialogFragment {

    private View customView;
    private ApplicationException applicationException = null;

    public interface IpAddressDialogListener {
        public void onDialogClick(String ipAddress);
    }

    private IpAddressDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            // Instantiate the listener so we can send events to the host
            mListener = (IpAddressDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString() + " does not implement the correct listener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        customView = inflater.inflate(R.layout.dialog_ip_address, null);

        builder.setMessage("Please enter your IP Address");
        builder.setView(customView);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Dont put anything here, this is handled in the onStart method so that the dialog does not disappear after validation
            }
        });

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        final AlertDialog d = (AlertDialog) getDialog();
        if (d != null) {
            Button positiveButton = (Button) d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean isValidInput = false;
                    EditText text = (EditText) customView.findViewById(R.id.dialog_ip_address_text);
                    String ipAddress = text.getText().toString();
                    if (ipAddress.matches("\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b")) {
                        isValidInput = true;
                    }
                    if (isValidInput) {
                        d.dismiss();
                        mListener.onDialogClick(ipAddress);
                    }
                }
            });
        }
    }

}
