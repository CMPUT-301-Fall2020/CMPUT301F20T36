package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.book_master.adapter.CustomImageList;
import com.example.book_master.models.Book;
import com.example.book_master.models.DBHelper;
import com.example.book_master.models.Image;

import java.util.ArrayList;

/**
 * US 01.06.01
 * As an owner, I want to view and edit a book description in my books.
 */
public class edit_book_activity extends AppCompatActivity {
    private EditText Title;
    private EditText Author;
    private Button Confirm;
    private Button Discard;
    private Button uploadImage;
    private Button deleteImage;

    private ArrayList<Image> imageList;
    private CustomImageList imageAdapter;
    private int imgPos;
    // Uri indicates, where the image will be picked from
    private Uri filePath;
    // request code for image uploading, pick a lucky number
    private final int PICK_IMAGE_REQUEST = 17;

    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_book);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        book = (Book) bundle.getSerializable("book_edit");

        Title = (EditText) findViewById(R.id.edit_book_name);
        Author = (EditText) findViewById(R.id.edit_book_author);
        Confirm = (Button) findViewById(R.id.edit_book_confirm);
        Discard = (Button) findViewById(R.id.edit_book_discard);
        uploadImage = (Button) findViewById(R.id.edit_book_uploadImage);
        deleteImage = (Button) findViewById(R.id.edit_book_deleteImage);

        // retrieve images being bundles to the current book from Firebase Storage
        // and display them in recyclerView
        imageList = new ArrayList<>();
        imageAdapter = new CustomImageList(imageList);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.edit_book_imagineRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(imageAdapter);
        DBHelper.retrieveImagine(imageList, imageAdapter, book.getISBN(), this);
        // get the ArrayList position of the current image being displayed (i.e, the one being viewed)
        imgPos = 0;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    imgPos = ((LinearLayoutManager)recyclerView.getLayoutManager())
                            .findFirstVisibleItemPosition();
                }
            }
        });
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        /**
         * US 08.01.01
         * As an owner, I want to optionally attach a photograph to a book of mine.
         */
        // upload an images to Firebase Storage
        // Reference:
        //  https://www.geeksforgeeks.org/android-how-to-upload-an-image-on-firebase-storage/
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        /**
         * US 08.02.01
         * As an owner, I want to delete any attached photograph for a book of mine.
         */
        deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper.deleteImage(imageList, imageAdapter, imgPos, book.getISBN(), edit_book_activity.this);
            }
        });

        Title.setText(book.getTitle());
        Author.setText(book.getAuthor());

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String book_title = Title.getText().toString();
                String book_Author = Author.getText().toString();

                if (!book_Author.equals("") && !book_title.equals("")) {
                    book.setTitle(book_title);
                    book.setAuthor(book_Author);

                    DBHelper.setBookDoc(book.getISBN(), book, edit_book_activity.this);

                    Intent intent = new Intent(edit_book_activity.this, check_list_activity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(edit_book_activity.this, "Field is not filled.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        Discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(edit_book_activity.this, check_list_activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // select image from mobile gallery
    private void selectImage() {
        // implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check request code and result code
        // upload image to Firebase Storage
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            // Get the data Uri
            filePath = data.getData();

            if (filePath != null) {
                DBHelper.uploadImagine(imageList, imageAdapter, filePath, book.getISBN(), edit_book_activity.this);
            }
        }
    }
}