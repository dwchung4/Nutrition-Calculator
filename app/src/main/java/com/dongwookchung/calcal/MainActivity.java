package com.dongwookchung.calcal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends Activity {
    Button buttonTakePhoto;
    Button buttonAnalyze;
    Button buttonCrop;
    Button buttonCropAnalyze;
    final int CAM_REQUEST = 1;
    final int CROP_REQUEST = 2;
    final String path = "sdcard/camera_app/food_image.jpeg";
    int count = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deleteFiles(new File("sdcard/camera_app"));

        buttonTakePhoto = (Button)findViewById(R.id.button_TakePhoto);
        buttonAnalyze = (Button)findViewById(R.id.button_Analyze);
        buttonCrop = (Button)findViewById(R.id.button_Crop);
        buttonCropAnalyze = (Button)findViewById(R.id.button_CropAnalyze);

        buttonTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFiles(new File("sdcard/camera_app"));
                ImageView image = (ImageView)findViewById(R.id.image_Food);
                image.setImageDrawable(null);
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));
                startActivityForResult(camera, CAM_REQUEST);
            }
        });

        buttonAnalyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File("sdcard/camera_app/food_image.jpeg");
                if (file.exists()) {
                    Intent intent = new Intent(getApplicationContext(), Identify.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Photo not available", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 2) {
                    Toast.makeText(getApplicationContext(), "You can identify at maximum 2 foods at a time",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        Intent cropIntent = new Intent("com.android.camera.action.CROP");
                        File file = new File(path);
                        Uri picUri = Uri.fromFile(file);
                        cropIntent.setDataAndType(picUri, "image/*");
                        cropIntent.putExtra("crop", "true");
                        cropIntent.putExtra("outputX", 256);
                        cropIntent.putExtra("outputY", 256);
                        cropIntent.putExtra("return-data", true);
                        startActivityForResult(cropIntent, CROP_REQUEST);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });

        buttonCropAnalyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File("sdcard/camera_app/food_image_0.jpeg");
                if (file.exists()) {
                    if (count == 1) {
                        Toast.makeText(getApplicationContext(), "You have only one image. Use \"Analyze Photo\" Button", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), IdentifyTwo.class);
                        startActivity(intent);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "You have no cropped images", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteFiles(File folder) {
        if (folder.exists()) {
            File[] files = folder.listFiles();
            for (int i = 0; i < files.length; i++) {
                files[i].delete();
            }
        }
        else {
            folder.mkdir();
        }
        count = 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CAM_REQUEST) {
                ImageView image = (ImageView) findViewById(R.id.image_Food);
                image.setImageDrawable(Drawable.createFromPath(path));
            }
            else if (requestCode == CROP_REQUEST) {
                try {
                    Bundle extras = data.getExtras();
                    Bitmap thePic = extras.getParcelable("data");
                    File crop_file = new File("sdcard/camera_app/food_image_" + count + ".jpeg");
                    count++;
                    FileOutputStream out = new FileOutputStream(crop_file);
                    thePic.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                    Toast.makeText(getApplicationContext(), "Cropped image saved", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}