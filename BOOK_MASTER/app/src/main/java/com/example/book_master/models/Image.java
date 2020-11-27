package com.example.book_master.models;

import com.google.firebase.storage.StorageReference;

/**
 * store one specific image info.
 */
public class Image {
    public String index;
    // the image could be directly displayed by Glide.load(imageRef)
    public StorageReference imageRef;

    /**
     * Constructor
     * @param index index of the image specified on Firebase Storage (e.g., file name)
     * @param imageRef reference to the image specified on Firebase Storage
     */
    public Image(String index, StorageReference imageRef) {
        this.index = index;
        this.imageRef = imageRef;
    }

    /**
     * @return index
     */
    public String getIndex() { return index; }

    /**
     * @return imageRef
     */
    public StorageReference getImageRef() { return imageRef; }
}
