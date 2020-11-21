package com.example.book_master.models;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.book_master.MainActivity;
import com.example.book_master.adapter.CustomImageList;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;
import java.util.ArrayList;

import javax.annotation.Nullable;

/**
 * Connect local data to Firebase.
 * Notice that the data retrieving is asynchronous with the main process.
 * Therefore we create listener and send the fetched data into static classes.
 * (UserList, BookList, MessageList)
 * o(*≧▽≦)ツ┏━┓
 */
public class DBHelper {

    private static final String firebaseRefURL = "gs://book-master-c3227.appspot.com/";
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String TAG = DBHelper.class.getSimpleName();

    /**
     * Create an new user account through Firebase Authentication
     * @param email user email, unique
     * @param password user password
     * @param username username, unique
     * @param contactInfo user contact information
     * @param context Context of the window where Toast should be displayed,
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

                            Log.d(TAG, "createUserWithEmailAndPassword: success");
                            Toast.makeText(context, "Authentication succeeded.",
                                    Toast.LENGTH_SHORT).show();

                            // include the user in the Firebase
                            setUserDoc(new User(email, password, username, contactInfo), context);
                        } else {
                            Log.w(TAG, "createUserWithEmailAndPassword: failure", task.getException());

                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Delete the current user account
     * @param context Context of the window where Toast should be displayed
     */
    public static void deleteAccount(final Context context) {
        final FirebaseUser user = mAuth.getCurrentUser();
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "delete account: success");
                            Toast.makeText(context, "User account deleting succeeded.",
                                    Toast.LENGTH_SHORT).show();

                            // also, delete the correspond User instance in Firebase
                            deleteUserDoc(context);
                            // direct UI to the login activity
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                        } else {
                            Log.w(TAG, "delete account: failure", task.getException());
                            Toast.makeText(context, "User account deleting failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Sign in the user account if it is recorded in Firebase
     * @param email user email, unique
     * @param password user password
     * @param context Context of the window where Toast should be displayed
     */
    public static void signIn(final String email, final String password, final Context context) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmailAndPassword: success");
                            Toast.makeText(context, "Authentication succeeded.",
                                    Toast.LENGTH_SHORT).show();

                            // update the current user
                            UserList.setCurrentUser(email);
                            // direct UI to main menu activity
                            Intent intent = new Intent(context, main_menu_activity.class);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        } else {
                            Log.w(TAG, "signInWithEmailAndPassword: failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Sign out the current user account
     * @param context Context of the window where Toast should be displayed
     */
    public static void signOut(final Context context) {
        mAuth.signOut();
    }

    /**
     * Create or modify the User instance for the current user in Firebase
     * @param user new (or updated) User instance
     * @param context Context of the window where Toast should be displayed
     */
    public static void setUserDoc(final User user, final Context context) {
        final FirebaseUser temp = mAuth.getCurrentUser();
        FirebaseFirestore mDB = FirebaseFirestore.getInstance();

        mDB.collection("User")
                .document(temp.getUid())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "set(user): success");
                        Toast.makeText(context, "User info updating succeeded.",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "set(user): failure", e);
                        Toast.makeText(context, "User info updating failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Create or modify one Book instance in Firebase
     * @param doc the unique ID identifying the book,
     *           retrieved via Book.getISBN()
     * @param book new (or updated) Book instance
     * @param context Context of the window where Toast should be displayed
     */
    public static void setBookDoc(final String doc, final Book book, final Context context) {
        FirebaseFirestore mDB = FirebaseFirestore.getInstance();
        mDB.collection("Book")
                .document(doc)
                .set(book)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "set(book): success");
                        Toast.makeText(context, "Book info updating succeeded.",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "set(book): failure", e);
                        Toast.makeText(context, "Book info updating failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Create or modify one Message instance in Firebase
     * @param doc the unique ID identifying the message,
     *           retrieved via String.valueOf(Message.hashCode())
     * @param msg new (or updated) Message instance
     * @param context Context of the window where Toast should be displayed
     */
    public static void setMessageDoc(final String doc, final Message msg, final Context context) {
        FirebaseFirestore mDB = FirebaseFirestore.getInstance();
        mDB.collection("Message")
                .document(doc)
                .set(msg)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "set(msg): success");
                        Toast.makeText(context, "Message info updating succeeded.",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "set(msg): failure", e);
                        Toast.makeText(context, "Message info updating failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Delete one User instance from Firebase
     * @param context Context of the window where Toast should be displayed
     */
    public static void deleteUserDoc(final Context context) {
        final FirebaseUser user = mAuth.getCurrentUser();
        FirebaseFirestore mDB = FirebaseFirestore.getInstance();
        mDB.collection("User")
                .document(user.getUid())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "delete User: success");
                        Toast.makeText(context, "User instance deleting succeeded.",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "delete User: failure", e);
                        Toast.makeText(context, "User instance deleting failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Delete one Book instance from Firebase
     * @param doc the unique ID identifying the book
     * @param context Context of the window where Toast should be displayed
     */
    public static void deleteBookDoc(final String doc, final Context context) {
        FirebaseFirestore mDB = FirebaseFirestore.getInstance();
        mDB.collection("Book")
                .document(doc)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "delete Book: success");
                        Toast.makeText(context, "Book instance deleting succeeded.",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "delete Book: failure", e);
                        Toast.makeText(context, "Book instance deleting failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Delete one Message instance from Firebase
     * @param doc the unique ID identifying the message
     * @param context Context of the window where Toast should be displayed
     */
    public static void deleteMessageDoc(final String doc, final Context context) {
        FirebaseFirestore mDB = FirebaseFirestore.getInstance();
        mDB.collection("Message")
                .document(doc)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "delete Message: success");
                        Toast.makeText(context, "Message instance deleting succeeded.",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "delete Message: failure", e);
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
                    MessageList.addMessage(new Message(sender, receiver, ISBN, status, longitude, latitude));
                }
            }
        });
    }

    /**
     * Upload image to Firebase Storage
     */
    public static void uploadImagine(final ArrayList<Image> imageList, final CustomImageList imageAdapter,
            final Uri URI, final String ISBN, final Context context) {
        // Code for showing progressDialog while uploading
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        // Reference to an image file in Cloud Storage
        final String index = UUID.randomUUID().toString() + ".png";
        final String imageRef = ISBN + "/" + index;
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl(firebaseRefURL);
        gsReference = gsReference.child(imageRef);

        final StorageReference finalGsReference = gsReference;
        gsReference.putFile(URI)
                .addOnSuccessListener(
                        new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // Image uploaded successfully
                                // Dismiss dialog
                                progressDialog.dismiss();
                                Log.d(TAG, "putFile(URI): success");
                                Toast.makeText(context, "Imagine uploading succeeded.",
                                        Toast.LENGTH_SHORT).show();
                                imageList.add(new Image(index, finalGsReference));
                                imageAdapter.setItems(imageList);
                                imageAdapter.notifyDataSetChanged();
                            }
                        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error, Image not uploaded
                        progressDialog.dismiss();
                        Log.w(TAG, "putFile(URI): failure", e);
                        Toast.makeText(context, "Imagine uploading failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(
                        new OnProgressListener<UploadTask.TaskSnapshot>() {
                            // Progress Listener for loading
                            // percentage on the dialog box
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage(
                                        "Uploaded " + (int)progress + "%");
                            }
                        });
    }

    /**
     * Delete imagines from Firebase Storage
     */
    public static void deleteImage(final ArrayList<Image> imageList, final CustomImageList imageAdapter,
            final int pos, final String ISBN, final Context context) {
        // Reference to an image file in Cloud Storage
        final String index = imageList.get(pos).getTitle();
        final String imageRef = ISBN + "/" + index;
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl(firebaseRefURL);
        gsReference = gsReference.child(imageRef);

        gsReference.delete().
                addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    // File deleted successfully
                        Log.d(TAG, "delete(): success");
                        Toast.makeText(context, "Imagine deleting succeeded.",
                                Toast.LENGTH_SHORT).show();
                        imageList.remove(pos);
                        imageAdapter.setItems(imageList);
                        imageAdapter.notifyDataSetChanged();
                }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Uh-oh, an error occurred!
                        Log.d(TAG, "onFailure: did not delete file");
                    }
                });
    }

    /**
     * Retrieve imagines from Firebase Storage
     */
    public static void retrieveImagine(final ArrayList<Image> imageList, final CustomImageList imageAdapter,
                                       final String ISBN, final Context context) {
        // Reference to an image file in Cloud Storage
        final String imageRef = ISBN;
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference gsReference = storage.getReferenceFromUrl(firebaseRefURL);
        gsReference = gsReference.child(imageRef);

        gsReference.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        imageList.clear();

                        for (StorageReference item : listResult.getItems()) {
                            imageList.add(new Image(item.getName(), item));
                        }
                        imageAdapter.setItems(imageList);
                        imageAdapter.notifyDataSetChanged();
                        Log.d(TAG, "listAll(): success");
                        Toast.makeText(context, "Imagine retrieving succeeded.",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "listAll(): failure", e);
                        Toast.makeText(context, "Imagine retrieving failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
