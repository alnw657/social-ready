package com.example.a5755.sosoready;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.IOException;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import java.io.OutputStream;

/**
 * Created by Alan on 2/7/2018.
 */

public class ImageEffect extends AppCompatActivity{
    private static final int IMAGE_COMPRESSION_QUALITY = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_effect);


        if (getIntent().hasExtra("byteArray")) {
            ImageView iv = (ImageView)findViewById(R.id.image_view);
            Bitmap bitmap = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);
            iv.setImageBitmap(bitmap);

        }

    }

    public void savebutton(View view) {

        ImageView iv = (ImageView)findViewById(R.id.image_view);
        iv.buildDrawingCache();
        Bitmap bm = iv.getDrawingCache();
        saveImageBitmap(bm);
        startActivity(new Intent(ImageEffect.this,SaveActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void normaleff(View view) {
        ImageView iv = (ImageView)findViewById(R.id.image_view);
       iv.setColorFilter(0, PorterDuff.Mode.DST );

    }

    public void bnweff(View view) {
        ImageView iv = (ImageView)findViewById(R.id.image_view);
       //iv.setColorFilter(0xCCffffff, PorterDuff.Mode.ADD );

        setBW(iv);


    }

    public void alaskaeff(View view) {
        ImageView iv = (ImageView)findViewById(R.id.image_view);
        iv.setColorFilter(0xCC08b0e7, PorterDuff.Mode.MULTIPLY );


    }

    public void pinkieeff(View view) {
        ImageView iv = (ImageView)findViewById(R.id.image_view);
        iv.setColorFilter(0xCCeb0571, PorterDuff.Mode.MULTIPLY );


    }

    public void redeff(View view) {
        ImageView iv = (ImageView)findViewById(R.id.image_view);
        iv.setColorFilter(0xDDa21801, PorterDuff.Mode.MULTIPLY );


    }

    private void saveImageBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            Log.e("TAG", "Bitmap is null. Cannot save image.");
            return;
        }

        // Use MediaStore API to insert the image into the device's MediaStore
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "image_" + System.currentTimeMillis() + ".jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
        } else {
            values.put(MediaStore.Images.Media.DATA, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath());
        }

        Uri imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        try {
            OutputStream os = getContentResolver().openOutputStream(imageUri);
            bitmap.compress(Bitmap.CompressFormat.JPEG, IMAGE_COMPRESSION_QUALITY, os);
            if (os != null) {
                os.close();
            }
            Toast.makeText(this, "Image saved successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("TAG", "Error saving image: " + e.getMessage());
            Toast.makeText(this, "Failed to save image", Toast.LENGTH_SHORT).show();
        }
    }


    private void setBW(ImageView iv){

        float brightness = 10;

        float[] colorMatrix = {
                0.33f, 0.33f, 0.33f, 0, brightness,
                0.33f, 0.33f, 0.33f, 0, brightness,
                0.33f, 0.33f, 0.33f, 0, brightness,
                0, 0, 0, 1, 0
        };

        ColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        iv.setColorFilter(colorFilter);
    }


}






