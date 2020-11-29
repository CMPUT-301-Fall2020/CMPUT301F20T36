package com.example.book_master.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.book_master.R;
import com.example.book_master.models.User;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class CustomUserList extends ArrayAdapter<User> {
    private ArrayList<User> userData;
    private Context context;

    public CustomUserList(Context context, ArrayList<User> userData) {
        super(context, 0, userData);
        this.context = context;
        this.userData = userData;
    }

    @Nullable
    @Override
    public View getView(int position, @androidx.annotation.Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.user_profile_contents, parent,false);
        }

        User user = userData.get(position);
        TextView usernmae = view.findViewById(R.id.user_profile_content_username);
        TextView contact = view.findViewById(R.id.userprofile_content_contact_info);

        usernmae.setText(user.getUsername());
        contact.setText(user.getContactInfo());

        return view;
    }
}
