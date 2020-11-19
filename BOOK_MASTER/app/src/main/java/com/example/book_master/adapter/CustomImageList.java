package com.example.book_master.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.book_master.R;
import com.example.book_master.models.Image;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class CustomImageList extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<Image> images;

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        public final View view;
        public final TextView titleView;
        public final ImageView imageView;

        public ImageViewHolder(View view) {
            super(view);
            this.view = view;
            titleView = null;
            imageView = view.findViewById(R.id.book_img);
        }
    }

    public CustomImageList(ArrayList<Image> images) { this.images = images; }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_photo_content, parent, false);
        context = view.getContext();

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageView imageView = ((ImageViewHolder) holder).imageView;
        Image image = images.get(position);
        StorageReference imageRef = image.getImageRef();
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .placeholder(R.mipmap.ic_launcher_round)
//                .error(R.mipmap.ic_launcher_round);

        Glide.with(context)
                .load(imageRef)
//                .apply(options)
                .into(imageView);
    }

    public void setItems(ArrayList<Image> images) {
        this.images = images;
    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return images.size();
    }
}

