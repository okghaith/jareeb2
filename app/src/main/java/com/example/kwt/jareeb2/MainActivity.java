package com.example.kwt.jareeb2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    ImageView imageView;
    Uri imageUri;
    Bitmap grayBitmap, imageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.imageView);


    }

    public void openGallery(View v)
    {
        Intent myIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(myIntent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && requestCode == RESULT_OK && data!=null)
        {
            imageUri = data.getData();

            //convert Uri to Bitmap
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

            imageView.setImageBitmap(imageBitmap);
        }

    }

    //when press on gray button
    public void convertToGray(View v)
    {
        Mat Rgba = new Mat();
        Mat grayMat = new Mat();

        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inDither = false;
        o.inSampleSize = 3;

        int width = imageBitmap.getWidth();
        int height = imageBitmap.getHeight();

        grayBitmap = Bitmap.createBitmap(width,height,Bitmap.Config.RGB_565);

        //convert bitmap to Mat

        Utils.bitmapToMat(imageBitmap,Rgba);

        //convert Rgba to Gray

        Imgproc.cvtColor(Rgba,grayMat,Imgproc.COLOR_RGB2GRAY);

        //convert Mat to bitmap again
        Utils.matToBitmap(grayMat,grayBitmap);

        imageView.setImageBitmap(grayBitmap);







    }
}
