package com.example.book_master.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.book_master.LoginActivity;
import com.example.book_master.ProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class DBHelper {
    public final String TAG = getClass().getSimpleName();
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDB;
    private Context context;

    private ArrayList<User> userList;

    public DBHelper(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseFirestore.getInstance();
        this.context = context;

        userList = new ArrayList<>();
        collectionListener();
    }

    public void createAccount(final String email, String password, final String username, final String contactInfo) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // create account success
                            Log.d(TAG, "createUserWithEmail:success");
                            Toast.makeText(context, "Authentication succeeded.",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            setUserDoc(user.getUid(), new User(username, contactInfo));
                        } else {
                            // create account fail
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // sign in success
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(context, "Authentication succeeded.",
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(context, ProfileActivity.class);
                            context.startActivity(intent);
                        } else {
                            // sign in fail
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void signOut() {
        mAuth.signOut();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public void deleteAccount() {
        FirebaseUser user = mAuth.getCurrentUser();
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");
                            Toast.makeText(context, "User account deleted.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public void setUserDoc(String doc, User user) {
        mDB.collection("User")
                .document(doc)
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "collection(collection).document(doc).set(obj):success");
                        Toast.makeText(context, "Info update succeeded.",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "collection(collection).document(doc).set(obj):failure", e);
                        Toast.makeText(context, "Info update failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    private void retrieveUserDoc(String doc) {
//        DocumentReference mDocRef = mDB.collection("User").document(doc);
//        mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                if (documentSnapshot.exists()) {
//                    User user = documentSnapshot.toObject(User.class);
//                } else {
//                    Log.d(TAG, "No such document");
//                }
//            }
//        });
//    }

    private void collectionListener() {
        CollectionReference mCollectionRef = mDB.collection("User");
        mCollectionRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                userList.clear();
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                    String username = (String) doc.getData().get("username");
                    String contactInfo = (String) doc.getData().get("contactInfo");
                    userList.add(new User(username, contactInfo));
//                    Toast.makeText(context, userList.get(0).getUsername(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public ArrayList<User> getUserList() {
        return userList;
    }
}
