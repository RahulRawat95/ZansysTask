package com.project.college.zansystask.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.project.college.zansystask.R;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Rahul on 23-Aug-17.
 */

public class CustomPagerAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    ArrayList<Uri> mImageUris;
    ArrayList<String> mDescriptions;

    public CustomPagerAdapter(Context context, ArrayList<Uri> uris, ArrayList<String> descriptions) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImageUris = uris;
        mDescriptions = descriptions;
    }

    @Override
    public int getCount() {
        return mImageUris.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.pager_item_imageView);
        imageView.setImageURI(mImageUris.get(position));
        try {
            imageView.setImageBitmap(CustomRowAdapter.getResizedBitmap(BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(mImageUris.get(position)))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
