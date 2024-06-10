package com.example.a5755.sosoready;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Alan on 2/7/2018.
 */

public class ImageEffect extends AppCompatActivity{
    private static final  String IMAGE_DIRECTORY = "/imageAPP";


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

    private String saveImageBitmap(Bitmap bitmap) {
ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90,bytes);

        File imageDirectory = new File(Environment.getExternalStorageDirectory()+IMAGE_DIRECTORY);
        if(!imageDirectory.exists())
            imageDirectory.mkdir();

        try {
            File jpg = new File(imageDirectory, Calendar.getInstance().getTimeInMillis()+".jpg");
            jpg.createNewFile();
            FileOutputStream fo = new FileOutputStream(jpg);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this, new String[]{jpg.getPath()}, new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "Saved !"+jpg.getAbsolutePath());
            return jpg.getAbsolutePath();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Unsucessful" + e, Toast.LENGTH_LONG).show();

        }
        return "";
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






