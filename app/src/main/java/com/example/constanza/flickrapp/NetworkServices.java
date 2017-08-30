package com.example.constanza.flickrapp;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Constanza on 27/08/2017.
 */

public class NetworkServices {

    private OkHttpClient client;
    public NetworkServices() {
        client = new OkHttpClient();
    }

    public String requestImages(String url) throws IOException{
        Request request = new Request.Builder().url(url).build();
        System.out.println("Request: " + request);
        Response response = client.newCall(request).execute();
        System.out.println("Response: " + response);
        return response.body().string();
    }
}
