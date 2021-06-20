package com.igalda.scrimgg;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

    private Bitmap image = null;

    @Override
    protected Bitmap doInBackground(String... strings) {
        String url = strings[0];
        try {
            InputStream in = new URL(url).openStream();
            this.image = BitmapFactory.decodeStream(in);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.image;
    }


    // use this method to download the image from a given url, and add it to the ImageView in your UI
    // this works, only if you type in AndroidManifiest.xml the next line of code, before the application tag:
        // <uses-permission android:name="android.permission.INTERNET"></uses-permission>
    public static void downloadTheImage(String url, ImageView theImageView){
        ImageDownloader imageDownloader = new ImageDownloader();
        try{
            Bitmap img = imageDownloader.execute(url).get();
            theImageView.setImageBitmap(img);
        }
        catch (ExecutionException e){
            e.printStackTrace();
        }
        catch(InterruptedException e1){
            e1.printStackTrace();
        }
    }
}
