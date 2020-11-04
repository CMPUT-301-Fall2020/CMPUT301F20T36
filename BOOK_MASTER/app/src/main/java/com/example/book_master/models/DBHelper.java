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

/**
 * o(*≧▽≦)ツ┏━┓
 */
public class DBHelper {
    private static final String TAG = DBHelper.class.getSimpleName();
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseFirestore mDB = FirebaseFirestore.getInstance();

    public static void createAccount(final String email,
                              String password,
                              final String username,
                              final String contactInfo,
                              final Context context) {
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
                            setUserDoc(user.getUid(), new User(username, contactInfo), context);
                        } else {
                            // create account fail
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static void signIn(String email, String password, final Context context) {
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

    public static void signOut(final Context context) {
        mAuth.signOut();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void deleteAccount(final Context context) {
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

    static void setUserDoc(String doc, User user, final Context context) {
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

    public static void collectionListener() {
        CollectionReference mCollectionRef = mDB.collection("User");
        mCollectionRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                UserList.clear();
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots) {
                    String username = (String) doc.getData().get("username");
                    String contactInfo = (String) doc.getData().get("contactInfo");
                    UserList.add(new User(username, contactInfo));
                }
            }
        });
    }
}
