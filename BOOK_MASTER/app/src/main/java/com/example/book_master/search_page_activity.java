package com.example.book_master;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
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

import com.example.book_master.adapter.CustomBorrowList;
import com.example.book_master.models.Book;
import com.example.book_master.models.BookList;
import com.example.book_master.models.Message;
import com.example.book_master.models.MessageList;
import com.example.book_master.models.UserList;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * US 03.01.01
 * As a borrower, I want to specify a keyword, and search for all books that are not currently accepted or borrowed whose description contains the keyword.
 * This activity class will let the user search the book which is not owned by him,
 * and is not in accepted and borrowed status
 */
public class search_page_activity extends Fragment {
    private Context mContext;

    private Button scan_ISBN;
    private Button search;
    private TextView keyword;
    private String ISBN;
    private ArrayList<Book> bookData;
    private ArrayAdapter<Book> bookAdapter;
    private ListView bookList;

    public static search_page_activity newInstance(String param1, String param2){
        search_page_activity fragment = new search_page_activity();
        Bundle args = new Bundle();
        args.putString("1", param1);
        args.putString("2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.search_page, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bookList = (ListView) view.findViewById(R.id.search_page_booklist);
        scan_ISBN = (Button) view.findViewById(R.id.search_bar_ISBN);
        search = (Button) view.findViewById(R.id.search_bar_confirm);
        keyword = (TextView) view.findViewById(R.id.search_bar_keyword);

        ISBN = "";  // pre define it to be empty
        bookData = BookList.getAvailableBook(UserList.getCurrentUser().getUsername());
        ArrayList<Book> temp = BookList.getAvailableBook(UserList.getCurrentUser().getUsername());
        for (Book book : bookData) {  // remove the book user requested
            ArrayList<Message> msglist = MessageList.searchISBN(book.getISBN());
            for (Message msg : msglist) {
                if (msg.getSender().equalsIgnoreCase(UserList.getCurrentUser().getUsername()) &&
                        msg.getStatus().equalsIgnoreCase(Book.REQUESTED)) {
                    temp.remove(book);
                    break;
                }
            }
        }
        bookData = temp;

        bookAdapter = new CustomBorrowList(getActivity(), bookData);
        bookList.setAdapter(bookAdapter);
        bookAdapter.notifyDataSetChanged();

        // if user click on a book, he will be direct to page with full info and send the request
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                
                Intent intent = new Intent(getActivity(), search_description.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("book", bookData.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, 3);
//                finish();
            }
        });

        // scan the ISBN from the book and store it in the keyword textview
        scan_ISBN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.setCaptureActivity(capture_activity.class);
                integrator.setOrientationLocked(false);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scanning ISBN");
                integrator.initiateScan();
            }
        });

        // search for matching book and update arrayadapter
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = keyword.getText().toString();
                bookAdapter.clear();

                if (text == null) {  // check if the text is reading error
                    Toast.makeText(getActivity(), "The input is null", Toast.LENGTH_SHORT).show();
                }
                else if (text.equalsIgnoreCase("")) {
                    bookData = BookList.getAvailableBook(UserList.getCurrentUser().getUsername());
                    Toast.makeText(getActivity(),
                            "Show all Book",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    bookData = BookList.searchDesc(text, UserList.getCurrentUser().getUsername());
                }
                bookAdapter = new CustomBorrowList(getActivity(), bookData);
                bookList.setAdapter(bookAdapter);
                bookAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * this will handle the result pass back from ISBN scanner which is used ZXing API
     * @param requestCode the requested code that call this function
     * @param resultCode the result code that is send to this function
     * @param intent the intent which contains the data in regular case
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode != 3){
            IntentResult scanISBN = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanISBN != null) {
                if (scanISBN.getContents() != null) {
                    String ISBN = scanISBN.getContents();
                    keyword.setText(ISBN);  // display the ISBN to keyword textview
                }
                else {
                    Toast.makeText(getActivity(), "No Results", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
            if (requestCode == 3 || resultCode == RESULT_OK) {
                bookAdapter.clear();
                bookData = BookList.getAvailableBook(UserList.getCurrentUser().getUsername());
                ArrayList<Book> temp = BookList.getAvailableBook(UserList.getCurrentUser().getUsername());

                for (Book book : bookData) {  // remove the book user requested
                    ArrayList<Message> msglist = MessageList.searchISBN(book.getISBN());
                    for (Message msg : msglist) {
                        if (msg.getSender().equalsIgnoreCase(UserList.getCurrentUser().getUsername()) &&
                                msg.getStatus().equalsIgnoreCase(Book.REQUESTED)) {
                            temp.remove(book);
                            break;
                        }
                    }
                }
                bookData = temp;

                bookAdapter = new CustomBorrowList(getActivity(), bookData);
                bookList.setAdapter(bookAdapter);
                bookAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onAttach(Context a){
        super.onAttach(a);
        mContext = a;
    }
}