package com.example.constanza.flickrapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvCategories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCategories = (ListView) findViewById(R.id.listCategories);

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category("Summer",getID("beach")));
        categories.add(new Category("Cars",getID("car")));
        categories.add(new Category("Movies",getID("film")));
        categories.add(new Category("Food",getID("food")));
        categories.add(new Category("Landscape",getID("forest")));
        categories.add(new Category("Sports",getID("sports")));
        categories.add(new Category("Travel",getID("travel")));

        ArrayAdapter<Category> adapter = new CustomAdapter(this, categories);

        lvCategories.setAdapter(adapter);
        lvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean connected = false;
                ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    //we are connected to a network
                    connected = true;
                }
                else {
                    connected = false;
                }

                if(!connected) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("NO INTERNET CONNECTION")
                            .setMessage("Can't show weather. No internet connection. :(")
                            .setPositiveButton("OK, got it", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setCancelable(false)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                } else {
                    Category chosenCat = (Category) lvCategories.getItemAtPosition(position);
                    String itemPos = chosenCat.getName();
                    Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                    intent.putExtra("itemPos", itemPos);
                    startActivity(intent);
                }
            }
        });
    }

    private int getID(String iconName) {
        int id;
        id = getBaseContext().getResources().getIdentifier(iconName,"mipmap",getBaseContext().getPackageName());
        return id;
    }

}
