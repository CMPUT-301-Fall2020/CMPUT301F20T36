package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.book_master.fragment.check_list;
import com.example.book_master.models.Book;
import com.example.book_master.models.BookList;
import com.example.book_master.models.UserList;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

// As an owner, I want to add a book in my books,
// each denoted with a clear, suitable description (at least title, author, and ISBN).
public class add_book_activity extends AppCompatActivity {
    private EditText TitleView;
    private EditText AuthorView;
    private Button ScanISBNBtn;
    private Button ConfirmBtn;
    private Button DiscardBtn;
    private String ISBN;

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
//        ISBN = "9781603092654";
//        retrieveBookInfo();

        // ------For Testing------
//        Random rand = new Random();
//        ISBN = Double.toString(rand.nextDouble());
        // ------End Here------

        ScanISBNBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(add_book_activity.this);
                integrator.setCaptureActivity(capture_ISBN__activity.class);
                integrator.setOrientationLocked(false);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scanning ISBN");
                integrator.initiateScan();
            }
        });

        ConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String book_title = TitleView.getText().toString();
                String book_Author = AuthorView.getText().toString();
                Book temp =  BookList.getBook(ISBN);
                if (!book_Author.equals("") && !book_title.equals("") && !ISBN.equals("")  && temp == null) {
                    Book book = new Book(book_title, book_Author, ISBN);
                    UserList.getCurrentUser().Add_Book_Owned(book, add_book_activity.this);

//                    Intent intent = new Intent(add_book_activity.this, check_list.class);
//                    startActivity(intent);
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
//                Intent intent = new Intent(add_book_activity.this, check_list.class);
//                startActivity(intent);
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
                retrieveBookInfo ();
            } else {
                Toast.makeText(this, "No Results", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }

    // As an owner, I want the book description by scanning it off the book (at least the ISBN).
    void retrieveBookInfo () {
        String bookApiUrl = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + ISBN;
        new JsonTask().execute(bookApiUrl);
    }

    // set up an asynchronous task to retrieve JASON file from URL,
    // parse the file to get book title and author
    // once done, update the corresponding EditView
    private class JsonTask extends AsyncTask<String, String, String> {
        // Reference:
        //  https://stackoverflow.com/questions/33229869/get-json-data-from-url-using-android
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                // fetched data
                JSONObject obj = new JSONObject(result);
                JSONObject item = obj
                    .getJSONArray("items")
                    .getJSONObject(0)
                    .getJSONObject("volumeInfo");

                TitleView.setText(item.getString("title"));
                String author = item.getString("authors");
                author = author.replaceAll("[\\[\\]\"]", "");
                AuthorView.setText(author);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
