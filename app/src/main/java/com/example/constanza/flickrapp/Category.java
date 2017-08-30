package com.example.constanza.flickrapp;

/**
 * Created by Constanza on 27/08/2017.
 */

public class Category {

    private String name;
    private int icon;

    public Category (String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }
}
