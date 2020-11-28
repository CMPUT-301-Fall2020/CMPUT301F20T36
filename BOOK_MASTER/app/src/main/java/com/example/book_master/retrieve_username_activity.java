package com.example.book_master;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.book_master.models.UserList;

/**
 * This activity class will allow user to input the username which they wants to search
 * then the profile of that user will be shown in a seperate activity
 */
public class retrieve_username_activity extends Fragment {
    TextView username;
    Button confirm, back;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.retrieve_username, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        username = (TextView) view.findViewById(R.id.retrieve_profile_username);
        confirm = (Button) view.findViewById(R.id.retrieve_profile_confirm);
        back = (Button) view.findViewById(R.id.retrieve_profile_discard);;

        // go to the page which shows the searched user
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString();
                if (name == null || UserList.getUser(name) == null) {
                    Toast.makeText(getActivity(), "The Person Does not Exist", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent retrieve_profile__intent = new Intent(getActivity(), retrieve_profile.class);

                    retrieve_profile__intent.putExtra("userName", username.getText().toString());
                    startActivity(retrieve_profile__intent);
                }
            }
        });
        // back to main menu

    }

    @Override
    public void onAttach(Context a){
        super.onAttach(a);
        mContext = a;
    }
}
