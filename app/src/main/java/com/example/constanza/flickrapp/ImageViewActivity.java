package com.example.constanza.flickrapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ImageViewActivity extends AppCompatActivity {

    NetworkServices client;
    String urlImage,idImage, api_key;
    ImageView imageView;
    TextView tvTitle, tvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        Intent intent = getIntent();
        client = new NetworkServices();
        urlImage = intent.getStringExtra("image");
        idImage = intent.getStringExtra("imageId");
        api_key = intent.getStringExtra("api_key");
        imageView = (ImageView) findViewById(R.id.imageView);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvUser = (TextView) findViewById(R.id.tvUser);

        final String infoURL = "https://api.flickr.com/services/rest/?method=flickr.photos.getInfo&photo_id=" +  idImage + "&api_key=" + api_key + "&format=json&nojsoncallback=1";

        Picasso.with(getBaseContext()).load(urlImage).fit().into(imageView);

        new AsyncTask<Void,Void,Void>() {
            String result;
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    result = client.requestImages(infoURL);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                JSONParser jsonParser = new JSONParser(result);

                try {
                    tvTitle.setText(jsonParser.getTitle());
                    tvUser.setText(jsonParser.getUser());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
}
