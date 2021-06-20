package com.igalda.scrimgg;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.igalda.scrimgg.dom.Champ;
import com.igalda.scrimgg.pers.PersistenciaChamp;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class allChamps extends BaseActivity {

    List<Champ> allChampList;
    LinearLayout verticalLL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_champs);
        View rootView = getLayoutInflater().inflate(R.layout.activity_all_champs, frameLayout);
        allChampList = PersistenciaChamp.getAllChamps();
        verticalLL = findViewById(R.id.verticalLL);
        showChamps();
    }


    public void showChamps(){
        LinearLayout llH=null;
        ImageView IV;
        for(int i = 0;i<allChampList.size();i++){
            if(i%2==0){
                llH = new LinearLayout(allChamps.this);
                llH.setOrientation(LinearLayout.HORIZONTAL);
                addLayout(llH,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
                IV = new ImageView(allChamps.this);
                new ImageLoadTask(allChampList.get(i).getImageURL(),IV);
                addView(IV,200,200,llH);
            }else{
                IV = new ImageView(allChamps.this);
                new ImageLoadTask(allChampList.get(i).getImageURL(),IV);
                addView(IV,200,200,llH);
            }
        }
    }

    public void addLayout(LinearLayout linearLayout, int width, int height){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,height);
        layoutParams.setMargins(10,0,10,0);
        linearLayout.setLayoutParams(layoutParams);
        verticalLL.addView(linearLayout);
    }

    public void addView (ImageView imageView, int width, int height,LinearLayout ll){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,height);
        layoutParams.setMargins(0,10,0,10);
        imageView.setLayoutParams(layoutParams);
        ll.addView(imageView);
    }

    //Funciona correctamente ya, no tocar más. USO: new ImageLoadTask(URL, IMAGEVIEW).execute();
    public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

        private String url;
        private ImageView imageView;

        public ImageLoadTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            imageView.setImageBitmap(result);
        }
}
}