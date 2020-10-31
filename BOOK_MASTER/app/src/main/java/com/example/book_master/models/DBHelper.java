package com.example.book_master.models;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.book_master.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class DBHelper {
    public final String TAG = getClass().getSimpleName();
    private FirebaseAuth mAuth;
    private FirebaseFirestore mDB;
    private Context context;

    public DBHelper(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseFirestore.getInstance();
        this.context = context;
    }

    public void createAccount(String email, String password, final String userName, final String contactInfo) {
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
                            setDoc("User", user.getUid(), new User(userName, contactInfo));
                            // updateUI(user);
                        } else {
                            // create account fail
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }
                    }
                });
    }

    public void setDoc(String collection, String doc, Object obj) {
        mDB.collection(collection)
                .document(doc)
                .set(obj)
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
                            // FirebaseUser user = mAuth.getCurrentUser();
                            // updateUI(user);
                        } else {
                            // sign in fail
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }
                    }
                });
    }

    public void signOut() {
        mAuth.signOut();
    }

    public void deleteAccount() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
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
    }
}
