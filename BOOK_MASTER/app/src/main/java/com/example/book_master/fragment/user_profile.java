package com.example.book_master.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.book_master.R;
import com.example.book_master.*;
import com.example.book_master.models.UserList;


public class user_profile extends Fragment {
    private Button edit, back;
    private TextView username, contact_info, email;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_page, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        edit = view.findViewById(R.id.profile_page_edit);
        back = view.findViewById(R.id.profile_page_back);
        username = view.findViewById(R.id.profile_page_username);
        contact_info = view.findViewById(R.id.profile_page_contact_info);
        email = view.findViewById(R.id.profile_page_email);
        username.setText("  " + UserList.getCurrentUser().getUsername());
        contact_info.setText("  " + UserList.getCurrentUser().getContactInfo());
        email.setText("  " + UserList.getCurrentUser().getEmail());
        // close the activity and back to main menu
        back.setVisibility(View.GONE);

        // allow the user to go to edit profile page
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), edit_profile_activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        contact_info.setText( "  " +UserList.getCurrentUser().getContactInfo());
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mContext = context;
    }
}
