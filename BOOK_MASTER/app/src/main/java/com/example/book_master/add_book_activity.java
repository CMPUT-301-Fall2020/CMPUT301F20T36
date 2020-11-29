package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.book_master.models.Book;
import com.example.book_master.models.BookList;
import com.example.book_master.models.UserList;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.okhttp.internal.http.StatusLine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Random;

/**
 * US 01.01.01
 * As an owner, I want to add a book in my books, each denoted with a clear, suitable description (at least title, author, and ISBN).
 */
public class add_book_activity extends AppCompatActivity {
    private EditText TitleView;
    private EditText AuthorView;
    private Button ScanISBNBtn;
    private Button ConfirmBtn;
    private Button DiscardBtn;
    private String ISBN;

    private String googleBookAPIKey = "AIzaSyBT1qsGZ5xY8QHhgGWANYvFqvTFOrIurFA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_book);

        TitleView = findViewById(R.id.add_book_name);
        AuthorView = findViewById(R.id.add_book_author);
        ScanISBNBtn = findViewById(R.id.add_edit_book_scan_isbn);
        ConfirmBtn = findViewById(R.id.add_edit_book_confirm);
        DiscardBtn = findViewById(R.id.add_edit_book_discard);
        ISBN = "";

        // ------For Testing------
        Random rand = new Random();
        ISBN = Double.toString(rand.nextDouble());
        ISBN = "0061964360";
        // ------End Here------

        String title = "";
        String author = "";
        try {
            retrieveBookInfo(title, author);
            TitleView.setText(title);
            AuthorView.setText(author);
        } catch (JSONException e) { e.printStackTrace(); }

        /**
         * TODO: US 01.02.01
         * As an owner, I want the book description by scanning it off the book (at least the ISBN).
         */
        ScanISBNBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(add_book_activity.this);
                integrator.setCaptureActivity(capture_ISBN__activity.class);
                integrator.setOrientationLocked(false);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scanning ISBN");
                integrator.initiateScan();

//                String title = "";
//                String author = "";
//                try {
//                    retrieveBookInfo(title, author);
//                    TitleView.setText(title);
//                    AuthorView.setText(author);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        });

        ConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String book_title = TitleView.getText().toString();
                String book_Author = AuthorView.getText().toString();
                Book temp =  BookList.getBook(ISBN);
                if (book_Author != "" && book_title != "" && ISBN != ""  && temp == null) {
                    Book book = new Book(book_title, book_Author, ISBN);
                    UserList.getCurrentUser().Add_Book_Owned(book, add_book_activity.this);

                    Intent intent = new Intent(add_book_activity.this, check_list_activity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(add_book_activity.this, "Field is not valid.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        DiscardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(add_book_activity.this, check_list_activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            if (scanningResult.getContents() != null) {
                ISBN = scanningResult.getContents();
            } else {
                Toast.makeText(this, "No Results", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }

    void retrieveBookInfo (String title, String author) throws JSONException {
        JSONObject responseJson = new GoogleApiRequest().doInBackground(ISBN);
        JSONObject item = responseJson
                .getJSONArray("items")
                .getJSONObject(0)
                .getJSONObject("volumeInfo");
        title = item.getString("title");
        JSONArray authorJSONArray = item.getJSONArray("authors");
        for (int i = 0; i < authorJSONArray.length(); i++) {
            if (i == 1) {author = authorJSONArray.getJSONObject(i).toString();}
            else {author += " & " + authorJSONArray.getJSONObject(i).toString();}
        }
    }

    // Reference:
    // https://stackoverflow.com/questions/14571478/using-google-books-api-in-android/45000302#45000302
    // Received ISBN from Barcode Scanner. Send to GoogleBooks to obtain book information.
    class GoogleApiRequest extends AsyncTask<String, Object, JSONObject>{
        @Override
        protected JSONObject doInBackground(String... ISBN) {
            // Stop if cancelled
            if(isCancelled()) {
                return null;
            }

            String apiUrlString = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + ISBN;

            try{
                HttpURLConnection connection = null;
                // Build Connection.
                try{
                    URL url = new URL(apiUrlString);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(5000); // 5 seconds
                    connection.setConnectTimeout(5000); // 5 seconds
                } catch (MalformedURLException e) {
                    // Impossible: The only two URLs used in the app are taken from string resources.
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    // Impossible: "GET" is a perfectly valid request method.
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                int responseCode = connection.getResponseCode();
                if(responseCode != 200){
                    Log.w(getClass().getName(), "GoogleBooksAPI request failed. Response Code: " + responseCode);
                    connection.disconnect();
                    return null;
                }

                // Read data from response.
                StringBuilder builder = new StringBuilder();
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = responseReader.readLine();
                while (line != null){
                    builder.append(line);
                    line = responseReader.readLine();
                }
                String responseString = builder.toString();
                Log.d(getClass().getName(), "Response String: " + responseString);
                JSONObject responseJson = new JSONObject(responseString);

                // Close connection and return response code.
                connection.disconnect();
                return responseJson;
            } catch (SocketTimeoutException e) {
                Log.w(getClass().getName(), "Connection timed out. Returning null");
                return null;
            } catch(IOException e) {
                Log.d(getClass().getName(), "IOException when connecting to Google Books API.");
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                Log.d(getClass().getName(), "JSONException when connecting to Google Books API.");
                e.printStackTrace();
                return null;
            }
        }
    }
}
