package com.example.book_master.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.book_master.MainActivity;
import com.example.book_master.main_menu_activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

/**
 * Connect local data to Firebase.
 * Notice that the data retrieving is asynchronous with the main process.
 * Therefore we create listener and send the fetched data into static classes.
 * (UserList, BookList, MessageList)
 * o(*≧▽≦)ツ┏━┓
 */
public class DBHelper {
    private static final String TAG = DBHelper.class.getSimpleName();
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    /**
     * Create an new user account through Firebase Authentication
     * @param email: user email, unique
     * @param password: user password
     * @param username: username, unique
     * @param contactInfo: user contact information
     * @param context: Context of the window where Toast should be displayed,
     *               e.g., in MainActivity, invoke MainActivity.this
     */
    public static void createAccount(final String email,
                                     final String password,
                                     final String username,
                                     final String contactInfo,
                                     final Context context) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmailAndPassword:success");
                            Toast.makeText(context, "Authentication succeeded.",
                                    Toast.LENGTH_SHORT).show();

                            // include the user in the Firebase
                            FirebaseUser user = mAuth.getCurrentUser();
                            setUserDoc(new User(email, password, username, contactInfo), context);
                        } else {
                            Log.w(TAG, "createUserWithEmailAndPassword:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Delete the current user account
     * @param context: Context of the window where Toast should be displayed
     */
    public static void deleteAccount(final Context context) {
        final FirebaseUser user = mAuth.getCurrentUser();
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "delete account:success");
                            Toast.makeText(context, "User account deleting succeeded.",
                                    Toast.LENGTH_SHORT).show();

                            // also, delete the correspond User instance in Firebase
                            deleteUserDoc(user.getUid(), context);
                            // direct UI to the login activity
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                        } else {
                            Log.w(TAG, "delete account:failure", task.getException());
                            Toast.makeText(context, "User account deleting failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Sign in the user account if it is recorded in Firebase
     * @param email: user email, unique
     * @param password: user password
     * @param context: Context of the window where Toast should be displayed
     */
    public static void signIn(final String email, final String password, final Context context) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmailAndPassword:success");
                            Toast.makeText(context, "Authentication succeeded.",
                                    Toast.LENGTH_SHORT).show();

                            // update the current user
                            UserList.setCurrentUser(email);
                            // direct UI to main menu activity
                            Intent intent = new Intent(context, main_menu_activity.class);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        } else {
                            Log.w(TAG, "signInWithEmailAndPassword:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Sign out the current user account
     * @param context: Context of the window where Toast should be displayed
     */
    public static void signOut(final Context context) {
        mAuth.signOut();

        // direct UI to login activity
//        Intent intent = new Intent(context, MainActivity.class);
//        context.startActivity(intent);
    }

    /**
     * Create or modify one User instance in Firebase
     * @param user: new (or updated) User instance
     * @param context: Context of the window where Toast should be displayed
     */
    public static void setUserDoc(final User user, final Context context) {
        FirebaseFirestore mDB = FirebaseFirestore.getInstance();
        final FirebaseUser temp = mAuth.getCurrentUser();

        mDB.collection("User")
                .document(temp.getUid())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "set(user):success");
                        Toast.makeText(context, "User info updating succeeded.",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "set(user):failure", e);
                        Toast.makeText(context, "User info updating failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Create or modify one Book instance in Firebase
     * @param doc: the unique ID identifying the book,
     *           retrieved via Book.getISBN()
     * @param book: new (or updated) Book instance
     * @param context: Context of the window where Toast should be displayed
     */
    public static void setBookDoc(final String doc, final Book book, final Context context) {
        FirebaseFirestore mDB = FirebaseFirestore.getInstance();
        mDB.collection("Book")
                .document(doc)
                .set(book)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "set(book):success");
                        Toast.makeText(context, "Book info updating succeeded.",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "set(book):failure", e);
                        Toast.makeText(context, "Book info updating failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Create or modify one Message instance in Firebase
     * @param doc: the unique ID identifying the message,
     *           retrieved via String.valueOf(Message.hashCode())
     * @param msg: new (or updated) Message instance
     * @param context: Context of the window where Toast should be displayed
     */
    public static void setMessageDoc(final String doc, final Message msg, final Context context) {
        FirebaseFirestore mDB = FirebaseFirestore.getInstance();
        mDB.collection("Message")
                .document(doc)
                .set(msg)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "set(msg):success");
                        Toast.makeText(context, "Message info updating succeeded.",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "set(msg):failure", e);
                        Toast.makeText(context, "Message info updating failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Delete one User instance from Firebase
     * @param doc: the user id generated by FirebaseAuth
     * @param context: Context of the window where Toast should be displayed
     */
    public static void deleteUserDoc(final String doc, final Context context) {
        FirebaseFirestore mDB = FirebaseFirestore.getInstance();
        mDB.collection("User")
                .document(doc)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "delete User:success");
                        Toast.makeText(context, "User instance deleting succeeded.",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "delete User:failure", e);
                        Toast.makeText(context, "User instance deleting failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Delete one Book instance from Firebase
     * @param doc: the unique ID identifying the book
     * @param context: Context of the window where Toast should be displayed
     */
    public static void deleteBookDoc(final String doc, final Context context) {
        FirebaseFirestore mDB = FirebaseFirestore.getInstance();
        mDB.collection("Book")
                .document(doc)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "delete Book:success");
                        Toast.makeText(context, "Book instance deleting succeeded.",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "delete Book:failure", e);
                        Toast.makeText(context, "Book instance deleting failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Delete one Message instance from Firebase
     * @param doc: the unique ID identifying the message
     * @param context: Context of the window where Toast should be displayed
     */
    public static void deleteMessageDoc(final String doc, final Context context) {
        FirebaseFirestore mDB = FirebaseFirestore.getInstance();
        mDB.collection("Message")
                .document(doc)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "delete Message:success");
                        Toast.makeText(context, "Message instance deleting succeeded.",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "delete Message:failure", e);
                        Toast.makeText(context, "Message instance deleting failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Listen to the change in Firebase and update userList in local
     * Should be called when application starts, i.e., in MainActivity
     */
    public static void userCollectionListener() {
        FirebaseFirestore mDB = FirebaseFirestore.getInstance();
        CollectionReference mCollectionRef = mDB.collection("User");
        mCollectionRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                UserList.clearList();
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                    String email = (String) doc.getData().get("email");
                    String password = (String) doc.getData().get("password");
                    String username = (String) doc.getData().get("username");
                    String contactInfo = (String) doc.getData().get("contactInfo");
                    UserList.addUser(new User(email, password, username, contactInfo));
                }
            }
        });
    }

    /**
     * Listen to the change in Firebase and update bookList in local
     * Should be called when application starts, i.e., in MainActivity
     */
    public static void bookCollectionListener() {
        FirebaseFirestore mDB = FirebaseFirestore.getInstance();
        CollectionReference mCollectionRef = mDB.collection("Book");
        mCollectionRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                BookList.clearList();
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {

                    String title = (String) doc.getData().get("title");
                    String author = (String) doc.getData().get("author");
                    String ISBN = (String) doc.getData().get("isbn");
                    String owner = (String) doc.getData().get("owner");
                    String borrower = (String) doc.getData().get("borrower");
                    String status = (String) doc.getData().get("status");
                    Book book = new Book(title, author, ISBN, owner, borrower);
                    book.setStatus(status);
                    BookList.addBook(book);

                }
            }
        });
    }

    /**
     * Listen to the change in Firebase and update messageList in local
     * Should be called when application starts, i.e., in MainActivity
     */
    public static void messageCollectionListener() {
        FirebaseFirestore mDB = FirebaseFirestore.getInstance();
        CollectionReference mCollectionRef = mDB.collection("Message");
        mCollectionRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                MessageList.clearList();
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {

                    String sender = (String) doc.getData().get("sender");
                    String receiver = (String) doc.getData().get("receiver");
                    String ISBN = (String) doc.getData().get("isbn");
                    String status = (String) doc.getData().get("status");
                    String longitude = (String) doc.getData().get("longitude");
                    String latitude = (String) doc.getData().get("latitude");
                    // TODO: include structure storing image
                    MessageList.addMessage(new Message(sender, receiver, ISBN, status, longitude, latitude));
                }
            }
        });
    }
}