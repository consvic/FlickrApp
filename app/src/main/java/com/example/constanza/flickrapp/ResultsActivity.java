package com.example.constanza.flickrapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends AppCompatActivity {

    NetworkServices client;
    String category;
    ArrayList<String> arrayList = new ArrayList<String>();
    ArrayList<String> arrayListId = new ArrayList<>();
    GridView gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Intent intent = getIntent();
        category = intent.getStringExtra("itemPos");
        client = new NetworkServices();
        final String api_key = "98037f6996d34f3b0cb6581da5abfdba";

        final String categoryURL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&text=" + category + "&api_key=" + api_key + "&per_page=10&format=json&nojsoncallback=1";


        new AsyncTask<Void,Void,Void>() {
            String result;
            JSONObject jsonObject;
            JSONArray jsonArray;
            JSONParser jsonParser;
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Log.d("url: ",categoryURL+"");
                    result = client.requestImages(categoryURL);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                jsonParser = new JSONParser(result);
                try {
                    jsonArray = jsonParser.getArray();

                    for (int i=0; i<9; i++) {
                        JSONObject temp = jsonArray.optJSONObject(i);
                        String farm = temp.getString("farm");
                        String id = temp.getString("id");
                        String server = temp.getString("server");
                        String secret = temp.getString("secret");
                        String url = "https://farm"+ farm +".staticflickr.com/"+ server +"/"+ id +"_"+ secret +".jpg";
                        arrayList.add(url);
                        arrayListId.add(id);
                        Log.d("urls images: ",url);

                    }

                    gridview = (GridView) findViewById(R.id.gridview);
                    ImageAdapter imageAdapter = new ImageAdapter(getBaseContext(),arrayList);
                    //imageAdapter.setArrayList(arrayList);
                    gridview.setAdapter(imageAdapter);


                    gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View v,
                                                int position, long id) {
                            String image = arrayList.get(position);
                            String imageId = arrayListId.get(position);
                            Log.d("itemPos",position+"");
                            Intent intent = new Intent(ResultsActivity.this, ImageViewActivity.class);
                            intent.putExtra("image",image);
                            intent.putExtra("imageId",imageId);
                            intent.putExtra("api_key",api_key);
                            startActivity(intent);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute();


    }
}
