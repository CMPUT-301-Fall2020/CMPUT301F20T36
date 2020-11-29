package com.example.book_master;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.book_master.R;
import com.example.book_master.models.BookList;
import com.example.book_master.models.DBHelper;
import com.example.book_master.models.Message;
import com.example.book_master.models.MessageList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class map_select_activity extends AppCompatActivity implements OnMapReadyCallback {
    public static final double EDMONTON_LATITUDE = 53.5461245f;
    public static final double EDMONTON_LONGITUDE = -113.4938229f;
    private TextView ISBN;
    private TextView borrower;
    private TextView bookName;
    private Button confirm;
    private SupportMapFragment mapFragment;
    private double latitude;
    private double longitude;
    private Message message;
    private int visibility;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geo_to_deliver);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        message = (Message) bundle.getSerializable("Message");
        visibility = bundle.getInt("Visibility");
        latitude = bundle.getDouble("Latitude");
        longitude = bundle.getDouble("Longitude");

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.geo_map);
        mapFragment.getMapAsync(this);

        ISBN = (TextView) findViewById(R.id.geo_ISBN);
        borrower = (TextView)findViewById(R.id.geo_borrower);
        bookName = (TextView) findViewById(R.id.Geo_borrower_Bookname);
        confirm = (Button) findViewById(R.id.geo_confirm_button);

        ISBN.setText(message.getISBN());
        borrower.setText(message.getReceiver());
        bookName.setText(BookList.getBook(message.getISBN()).getTitle());

        if (visibility == 1) {
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBHelper.deleteMessageDoc(String.valueOf(message.hashCode()), map_select_activity.this);
                    message.setLatitude(Double.toString(latitude));
                    message.setLongitude(Double.toString(longitude));
                    DBHelper.setMessageDoc(String.valueOf(message.hashCode()), message, map_select_activity.this);
                    finish();
                }
            });
        } else {
            confirm.setText("Back");
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
//                Log.d("System out", "onMarkerDragStart..."+marker.getPosition().latitude+"..."+marker.getPosition().longitude);
//                latitude = marker.getPosition().latitude;
//                longitude = marker.getPosition().longitude;
            }

            @Override
            public void onMarkerDrag(Marker marker) {
//                Log.d("System out", "onMarkerDragEnd..."+marker.getPosition().latitude+"..."+marker.getPosition().longitude);
                latitude = marker.getPosition().latitude;
                longitude = marker.getPosition().longitude;
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
//                Log.i("System out", "onMarkerDrag...");
            }
        });

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title("Marker in Edmonton")
                .draggable(true));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 12.0f));
    }
}
