package com.example.book_master;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.book_master.models.DBHelper;
import com.example.book_master.models.UserList;

import javax.annotation.Nullable;

/**
 * This fragment will all the user to sign up with all required inputs
 */
public class RegisterFrag extends DialogFragment {
    private EditText emailText;
    private EditText passwordText;
    private EditText usernameText;
    private EditText contactInfoText;

    private OnFragmentInteractionListener listener;
    public interface OnFragmentInteractionListener {
//        void onRegisterPressed();
//        void onCancelPressed();
    }

    // take in an Item and store it in the Fragment's Bundle object
    // other methods could access the Bundle using getArguments() and retrieve the Item object
    static RegisterFrag newInstance(String email, String password) {
        Bundle args = new Bundle();
        args.putSerializable("email", email);
        args.putSerializable("password", password);

        RegisterFrag fragment = new RegisterFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // return super.onCreateDialog(savedInstanceState);
        // inflate the layout for this fragment
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.register, null);

        emailText = view.findViewById(R.id.email_register);
        passwordText = view.findViewById(R.id.password_register);
        usernameText = view.findViewById(R.id.username_register);
        contactInfoText = view.findViewById(R.id.contactInfo_register);

        final String email = (String) getArguments().getSerializable("email");
        if (email != null) { emailText.setText(email); }
        final String password = (String) getArguments().getSerializable("password");
        if (password != null) { passwordText.setText(password); }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Create_Account")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        final String email = emailText.getText().toString();
                        final String password = passwordText.getText().toString();
                        final String username = usernameText.getText().toString();
                        final String contactInfo = contactInfoText.getText().toString();

                        // check if the username is unique
                        if (UserList.checkUnique(username) == false) {
                            Toast.makeText(view.getContext(), "Username already existed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            DBHelper.createAccount(email, password, username, contactInfo, view.getContext());
                        }
                    }
                }).create();
    }
}
