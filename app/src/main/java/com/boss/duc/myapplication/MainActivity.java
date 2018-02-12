package com.boss.duc.myapplication;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    ImageView img;
    ProgressBar progressBar;
    TextView txtprogress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img=(ImageView)findViewById(R.id.img);
        progressBar=findViewById(R.id.pvgbar);
        txtprogress=findViewById(R.id.txtprogress);
        new DownloadImageTask().execute("http://globalmedicalco.com/photos/globalmedicalco/12/59232.jpg");
    }
    class DownloadImageTask extends AsyncTask<String,Integer,Drawable>{
        byte[] bytes;
        Drawable drawable=null;
        @Override
        protected Drawable doInBackground(String... strings) {

            try {
                int count=0;
                long total=0;
                URL url=new URL(strings[0]);
                URLConnection urlConnection=url.openConnection();
                InputStream inputStream=urlConnection.getInputStream();
                BufferedInputStream bufferedInputStream=new BufferedInputStream(inputStream);
                int length=urlConnection.getContentLength();
                bytes=new byte[length];
                while (count<length){
                    count++;
                    publishProgress((count*100)/length);
                }

                drawable=Drawable.createFromStream(inputStream,"src");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return drawable;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
            txtprogress.setText(String.valueOf(values[0])+"%%%%%%%%%%%%%%%%");
			
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            super.onPostExecute(drawable);
            img.setImageDrawable(drawable);
        }
    }
}
