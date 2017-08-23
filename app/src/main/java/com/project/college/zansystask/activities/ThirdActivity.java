package com.project.college.zansystask.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.project.college.zansystask.R;
import com.project.college.zansystask.adapters.CustomRowAdapter;

import java.util.ArrayList;

/**
 * Created by Rahul on 23-Aug-17.
 */

public class ThirdActivity extends AppCompatActivity {

    public static final String DESCRIPTION_LIST = "descriptionList";

    private ArrayList<Uri> mImageURI;
    private ArrayList<String> mImageCaptions;

    private RecyclerView mRecyclerView;
    private CustomRowAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Bundle args = getIntent().getBundleExtra("BUNDLE");
        mImageURI = (ArrayList<Uri>) args.getSerializable(SecondActivity.IMAGES_URI);
        mImageCaptions = (ArrayList<String>) args.getSerializable(ThirdActivity.DESCRIPTION_LIST);

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_third_recycler_view);
        mAdapter = new CustomRowAdapter(this, mImageURI, mImageCaptions);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }
}
