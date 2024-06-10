package com.example.a5755.sosoready;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private Button cambtn;
    private Button photobtn;
    String stringPath;

    private static final  String IMAGE_DIRECTORY = "/imageAPP";
    private final int GALLERY = 1, CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cambtn = (Button)findViewById(R.id.opencamerabutton);
        photobtn = (Button)findViewById(R.id.openphotosbutton);

    }

    public void OpenCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    public void OpenPhotos(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_CANCELED)
            return;

        if(requestCode == GALLERY)
        {
            if(data != null)
            {
                Uri contentUri = data.getData();
               try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentUri);

                   Intent intent = new Intent(this, ImageEffect.class);

                   ByteArrayOutputStream bs = new ByteArrayOutputStream();
                   bitmap.compress(Bitmap.CompressFormat.JPEG, 20, bs);
                   intent.putExtra("byteArray", bs.toByteArray());
                   startActivity(intent);
                   overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
                catch (IOException e)
                {
                    e.printStackTrace();
                    Toast.makeText(this, "Not Successful"+e, Toast.LENGTH_LONG).show();
                }


            }
        }
        else if (requestCode == CAMERA)
        {

                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                Intent takephoto =  new Intent(this,ImageEffect.class);
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.PNG, 50, bs);
            takephoto.putExtra("byteArray", bs.toByteArray());
            startActivity(takephoto);



        }
    }

    }



