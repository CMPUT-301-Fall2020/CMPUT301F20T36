package com.example.book_master.models;

import com.example.book_master.models.*;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Database {
    private FirebaseFirestore db;

    private static final String TAG = "Database";

    public void Database() {
        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();
    }

    /**
     * Set attributes for an instance
     * @param className     class name, referring to one collection in Firebase
     * @param instanceName  instance name, referring to one document in Firebase
     * @param key           key, accessing the object need to be stored
     * @param obj           the object to be stored
     */
    public void setAttribute(String className, String instanceName, String key, Object obj) {
        Map<String, Object> item = new HashMap<>();
        item.put(key, obj);

        db.collection(className)
                .document(instanceName)
                .set(item)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
    }

    /**
     * Update attributes for an instance
     * @param className     class name, referring to one collection in Firebase
     * @param instanceName  instance name, referring to one document in Firebase
     * @param key           key, accessing the object need to be stored
     * @param obj           the object to be updated
     */
    public void updateAttribute (String className, String instanceName, String key, Object obj) {
        DocumentReference ref = db.collection(className).document(instanceName);
        ref.update(key, obj);
    }
}
