package com.example.contact_directory.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.contact_directory.R;
import com.example.contact_directory.activity.ContactDetailActivity;

import java.util.Objects;

public class ConfirmDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.confirm_action_message)
                .setTitle(R.string.delete_contact_message)
                .setPositiveButton(R.string.button_confirm, (dialog, id) -> ((ContactDetailActivity) requireActivity()).okClicked())
                .setNegativeButton(R.string.button_cancel, (dialog, id) -> ((ContactDetailActivity) requireActivity()).cancelClicked());

        return builder.create();
    }
}
