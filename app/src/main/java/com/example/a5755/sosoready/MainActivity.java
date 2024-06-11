package com.example.a5755.sosoready;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import java.io.ByteArrayOutputStream;
import java.io.IOException;



public class MainActivity extends AppCompatActivity {

    private Button cambtn;
    private Button photobtn;
    String stringPath;

    private final int GALLERY = 1, CAMERA = 2;

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;
    //private static final int CAMERA_REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cambtn = (Button)findViewById(R.id.opencamerabutton);
        photobtn = (Button)findViewById(R.id.openphotosbutton);

    }

 //   public void OpenCamera(View view) {
    //    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      //  startActivityForResult(intent, CAMERA);

    //}
    public void OpenCamera(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Permission not granted, request it
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        } else {
            // Permission granted, open camera
            startCamera();
        }
    }

    private void startCamera() {
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
                   bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bs);
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
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bs);
            takephoto.putExtra("byteArray", bs.toByteArray());
            startActivity(takephoto);



        }
    }

    }



