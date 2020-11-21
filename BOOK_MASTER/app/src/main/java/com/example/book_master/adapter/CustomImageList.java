package com.example.book_master.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.book_master.R;
import com.example.book_master.models.Image;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class CustomImageList extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Image> imageList;

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final ImageView imageView;

        public ImageViewHolder(View view) {
            super(view);
            this.view = view;
            imageView = view.findViewById(R.id.image_content_bookImagine);
        }
    }

    public CustomImageList(ArrayList<Image> imageList) { this.imageList = imageList; }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_content, parent, false);
        context = view.getContext();

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Image image = imageList.get(position);
        ImageView imageView = ((ImageViewHolder) holder).imageView;

        imageView.setContentDescription(image.getTitle());
        StorageReference imageRef = image.getImageRef();
        Glide.with(context)
                .load(imageRef)
                .into(imageView);
    }

    public void setItems(ArrayList<Image> imageList) {
        this.imageList = imageList;
    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return imageList.size();
    }
}

