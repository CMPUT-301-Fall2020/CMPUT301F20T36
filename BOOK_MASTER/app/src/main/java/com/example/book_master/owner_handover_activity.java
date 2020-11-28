package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.book_master.models.Book;
import com.example.book_master.models.BookList;
import com.example.book_master.models.DBHelper;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.example.book_master.models.Message;



public class owner_handover_activity extends AppCompatActivity implements View.OnClickListener{
    private Button Scan_Button;
    private Button Hand_Over;
    private TextView ISBN_Display;
    private String ISBN;
    private Book book;
    private Message message;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.owner_handover);
        ISBN = "";
//        message = (Message) bundle.getSerializable("message");

        Scan_Button = (Button) findViewById(R.id.Owner_handover_ISBNbutton);
        Hand_Over = (Button) findViewById(R.id.Owner_handover_deliverButton);

        ISBN_Display = (TextView) findViewById(R.id.Owner_handover_name);

        Scan_Button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(this);
                integrator.setCaptureActivity(capture_activity.class);
                integrator.setOrientationLocked(false);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scanning ISBN");
                integrator.initiateScan();
            }
        });
        Hand_Over.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // Set status of the book with ISBN as "Borrowed" & send message to borrower
//                DBHelper.setUserDoc(String.valueOf(message.hashCode()), owner_handover_activity.this);
//                message.setStatus("BORROWED");
//                DBHelper.setMessageDoc(String.valueOf(message.hashCode()), message,owner_handover_activity.this);
//
//                finish();
//            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            if (scanningResult.getContents() != null) {
                ISBN = scanningResult.getContents();
                ISBN_Display.setText("ISBN: " + ISBN);
            }
            else {
                Toast.makeText(this, "No Results", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }
}