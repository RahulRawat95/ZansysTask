package com.project.college.zansystask.activities;

import android.Manifest;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.project.college.zansystask.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_IMAGES = 1;
    private static final int REQUEST_CODE_STORAGE = 2;

    private Button mSelectImages;
    private ArrayList<Uri> mArrayUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSelectImages = (Button) findViewById(R.id.activity_main_select_images);

        mSelectImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE);
                } else {
                    selectImages();
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mArrayUri != null) {
                    if (mArrayUri.size() != 0) {
                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                        Bundle args = new Bundle();
                        args.putSerializable(SecondActivity.IMAGES_URI, mArrayUri);
                        intent.putExtra("BUNDLE", args);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Please Select Images First", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please Select Images First", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGES && resultCode == RESULT_OK && data != null) {
            mArrayUri = new ArrayList<Uri>();
            if (data.getData() != null) {
                mArrayUri.add(data.getData());
            } else {
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        mArrayUri.add(uri);
                    }
                }
            }
            int i = mArrayUri.size();
            mSelectImages.setText(i + " image" + (i > 1 ? "s " : " ") + "selected");
        } else {
            mSelectImages.setText(R.string.select_images);
            if (mArrayUri != null)
                mArrayUri.clear();
        }
    }

    public boolean checkPermission(String permission) {
        return (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED);
    }

    public void selectImages() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGES);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_STORAGE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImages();
            } else {
                new AlertDialog.Builder(this).setMessage("This application requires Storage Permission to work")
                        .setCancelable(false)
                        .setPositiveButton("Open App Settings",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent();
                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                                        intent.setData(uri);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).create().show();
            }
        }
    }
}