package com.example.book_master;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.book_master.adapter.CustomUserList;
import com.example.book_master.models.Book;
import com.example.book_master.models.User;
import com.example.book_master.models.UserList;

import java.util.ArrayList;

/**
 * This activity class will allow user to input the username which they wants to search
 * then the profile of that user will be shown in a seperate activity
 */
public class retrieve_username_activity extends Fragment {
    private TextView username;
    private Button search;
    private ArrayList<User> userData;
    private ArrayAdapter<User> userAdapter;
    private ListView userList;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_username_page, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        username = (TextView) view.findViewById(R.id.search_username_bar_keyword);
        search = (Button) view.findViewById(R.id.search_username_bar_confirm);
        userList = (ListView) view.findViewById(R.id.search_username_page_userlist);

        userData = new ArrayList<User>();
        userAdapter = new CustomUserList(getActivity(), userData);
        userAdapter.notifyDataSetChanged();
        userList.setAdapter(userAdapter);
        userAdapter.notifyDataSetChanged();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username == null | username.getText().equals("")) {
                    Toast.makeText(getActivity(),"The input is null", Toast.LENGTH_SHORT).show();
                }
                else {
                    userAdapter.clear();
                    userData = UserList.searchDesc(username.getText().toString());
                    userAdapter = new CustomUserList(getActivity(), userData);
                    userAdapter.notifyDataSetChanged();
                    userList.setAdapter(userAdapter);
                }
            }
        });

        userList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                Intent intent = new Intent(getActivity(), retrieve_profile.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", userData.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
//                finish();
            }
        });
    }

    @Override
    public void onAttach(Context a){
        super.onAttach(a);
        mContext = a;
    }
}
