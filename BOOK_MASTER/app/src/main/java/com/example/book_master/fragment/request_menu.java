package com.example.book_master.fragment;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.book_master.R;
import com.example.book_master.adapter.CustomRequestList;
import com.example.book_master.models.Book;
import com.example.book_master.models.Message;
import com.example.book_master.models.MessageList;
import com.example.book_master.models.UserList;
import com.example.book_master.request_description;

import java.util.ArrayList;

public class request_menu extends Fragment {
    ListView requestList;
    TextView requestListTitle;
    ArrayList<Message> messData = new ArrayList<>();
    ArrayAdapter<Message> messAdapter;
    Spinner spinner_status;
    ArrayAdapter<String> spinnerAdapter_status, spinnerAdapter_mode;
    String mode;
    Context mContext;

    // Create a new request_menu instance given an input argument for different mode (sender or receiver)
    public static request_menu newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("mode", name);
        request_menu fragment = new request_menu();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.request_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestList = view.findViewById(R.id.Request_list);
        requestListTitle = view.findViewById(R.id.Request_list_title);
        spinner_status = view.findViewById(R.id.request_status_spinner);
        // get the mode argument to determine which mode it is in
        Bundle bundle = getArguments();
        if (bundle != null) {
            mode = bundle.get("mode").toString();
            requestListTitle.setText("           Request List");
            final String name = UserList.getCurrentUser().getUsername();
            // status for setting up the spinner
            final String[] status = {"All", Book.REQUESTED, Book.ACCEPTED, Book.BORROWED, Book.RETURN};

            messData = MessageList.searchSender(name);
            messAdapter = new CustomRequestList(getActivity(), messData);
            requestList.setAdapter(messAdapter);
            // set up a list view for request list
            requestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getActivity(), request_description.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("message", messData.get(position));
                    bundle.putSerializable("status", messData.get(position).getStatus());
                    bundle.putSerializable("mode", mode);
                    intent.putExtras(bundle);
                    startActivity(intent);
//                    getActivity().finish();
                }
            });

            spinnerAdapter_status = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, status);
            spinnerAdapter_status.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_status.setAdapter(spinnerAdapter_status);

            // setting up the spinner as a filter of status
            spinner_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    messAdapter.clear();
                    // 0 position is for displaying request for all status
                    if (position == 0) {
                        // mode is for select sender and receiver
                        if (mode.equals("SENT")) {
                            messData = MessageList.searchSender(name);
                        } else {
                            messData = MessageList.searchReceiver(name);
                        }
                    } else {
                        // If it is not in position 0, select the status that match the one on spinner
                        ArrayList<Message> temp = new ArrayList<>();
                        if (mode.equals("SENT")) {
                            for (Message i : MessageList.searchSender(name)) {
                                if (i.getStatus().equals(status[position])) {
                                    temp.add(i);
                                }
                            }
                        } else {
                            for (Message i : MessageList.searchReceiver(name)) {
                                if (i.getStatus().equals(status[position])) {
                                    temp.add(i);
                                }
                            }
                        }
                        messData = temp;
                    }
                    messAdapter = new CustomRequestList(getActivity(), messData);
                    requestList.setAdapter(messAdapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) { }
            });
        }
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onResume() {
        super.onResume();

        messData = MessageList.searchSender(UserList.getCurrentUser().getUsername());
        messAdapter = new CustomRequestList(getActivity(), messData);
        requestList.setAdapter(messAdapter);
    }
}
