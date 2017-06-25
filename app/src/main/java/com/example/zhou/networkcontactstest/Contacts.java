package com.example.zhou.networkcontactstest;

/**
 * Created by zhou on 2017/6/25.
 */

public class Contacts {

    private String name;

    private String telephone;

    private String imageUrl;

    public Contacts(String name, String telephone, String imageUrl){
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

    public String getimageUrl(){
        return imageUrl;
    }
}
