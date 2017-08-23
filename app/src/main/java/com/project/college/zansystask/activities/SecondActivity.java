package com.project.college.zansystask.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.project.college.zansystask.R;
import com.project.college.zansystask.adapters.CustomPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Rahul on 23-Aug-17.
 */

public class SecondActivity extends AppCompatActivity {

    String TAG = "dexter";
    public static final String IMAGES_URI = "arraylistOfImages";

    private ArrayList<Uri> mImageURI;
    private ArrayList<String> mImageCaptions;

    private ViewPager mViewPager;
    private CustomPagerAdapter mCustomPagerAdapter;
    private EditText mEditText;
    private FloatingActionButton mNext;
    private int mPreviousPos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle args = getIntent().getBundleExtra("BUNDLE");
        mImageURI = (ArrayList<Uri>) args.getSerializable(IMAGES_URI);
        mImageCaptions = new ArrayList<String>(mImageURI.size());

        for (int i = 0; i < mImageURI.size(); i++)
            mImageCaptions.add("");

        mEditText = (EditText) findViewById(R.id.activity_second_edittext);
        mEditText.setText("");
        mNext = (FloatingActionButton) findViewById(R.id.activity_second_fab);

        mCustomPagerAdapter = new CustomPagerAdapter(this, mImageURI, mImageCaptions);
        mViewPager = (ViewPager) findViewById(R.id.activity_second_view_pager);
        mViewPager.setAdapter(mCustomPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mImageCaptions.set(mPreviousPos, mEditText.getText().toString());

                mPreviousPos = position;

                mEditText.setText(mImageCaptions.get(position));
                mEditText.setSelection(mImageCaptions.get(position).length());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImageCaptions.set(mPreviousPos, mEditText.getText().toString());

                Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                Bundle args = new Bundle();
                args.putSerializable(SecondActivity.IMAGES_URI, mImageURI);
                args.putSerializable(ThirdActivity.DESCRIPTION_LIST, mImageCaptions);
                intent.putExtra("BUNDLE", args);
                startActivity(intent);
            }
        });
    }
}
