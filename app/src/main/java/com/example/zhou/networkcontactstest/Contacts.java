package com.example.zhou.networkcontactstest;

import android.graphics.Bitmap;

/**
 * Created by zhou on 2017/6/25.
 */

public class Contacts {

    private String name;

    private String telephone;

    private Bitmap imageUrl;

    public Contacts(String name, String telephone, Bitmap imageUrl){
        this.name = name;
        this.telephone = telephone;
        this.imageUrl = imageUrl;
    }

    public  String getName(){
        return name;
    }

    public String getTelephone(){
        return  telephone;
    }

    public Bitmap getimageUrl(){
        return imageUrl;
    }
}
