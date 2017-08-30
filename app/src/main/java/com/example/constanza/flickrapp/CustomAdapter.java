package com.example.constanza.flickrapp;

import android.content.Context;
import android.media.audiofx.AudioEffect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Constanza on 27/08/2017.
 */

public class CustomAdapter extends ArrayAdapter {
    private TextView tvName;
    private ImageView ivIcon;

    public CustomAdapter(@NonNull Context context, ArrayList<Category> categories) {
        super(context, R.layout.my_layout, categories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //TODO 1. Definir LayoutInflater
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        //TODO 2. Asociar LayoutInflater  a la vista custom
        View customView = layoutInflater.inflate(R.layout.my_layout, parent, false);

        //TODO 3. Obtener elemento  del arreglo en la posición
        Category category = (Category) getItem(position);

        //TODO 4. Definir TextView e imageView
        tvName = (TextView) customView.findViewById(R.id.category);
        ivIcon = (ImageView) customView.findViewById(R.id.list_image);
        int id = category.getIcon();
        //int id = getContext().getResources().getIdentifier("mipmap-ldpi/car",null,null);
        //int id = getContext().getResources().getIdentifier("mipmap/car",null,null);

        Log.d("id icon",id+"");
        tvName.setText(category.getName());
        ivIcon.setImageResource(id);

        return customView;

    }
}
