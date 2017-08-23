package com.project.college.zansystask.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.college.zansystask.R;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Rahul on 23-Aug-17.
 */

public class CustomRowAdapter extends RecyclerView.Adapter<CustomRowAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Uri> mImageURI;
    private ArrayList<String> mImageCaptions;

    public CustomRowAdapter(Context context, ArrayList<Uri> images, ArrayList<String> descriptions) {
        mContext = context;
        mImageURI = images;
        mImageCaptions = descriptions;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.description.setText(mImageCaptions.get(position));
        try {
            holder.imageView.setImageBitmap(getResizedBitmap(BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(mImageURI.get(position)))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mImageURI.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView description;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            description = (TextView) view.findViewById(R.id.row_recycler_view_title);
            imageView = (ImageView) view.findViewById(R.id.row_recycler_view_cover_image);
        }
    }

    public Bitmap getResizedBitmap(Bitmap image) {
        int maxSize = 512;
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
}
