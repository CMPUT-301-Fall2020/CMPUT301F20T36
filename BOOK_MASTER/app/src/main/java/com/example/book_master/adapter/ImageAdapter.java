package com.example.book_master.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.book_master.R;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter {

    private ArrayList<String> imageTitles;

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        public final ImageView image;

        public ImageViewHolder(View view) {
            super(view);
            image = itemView.findViewById(R.id.book_img);
        }
    }

    public ImageAdapter(ArrayList<String> ImageNames) {
        this.imageTitles = ImageNames;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_description, parent, false);

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) { }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return imageTitles.size();
    }
}

