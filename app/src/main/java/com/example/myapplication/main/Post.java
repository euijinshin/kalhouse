package com.example.myapplication.main;

import com.google.gson.annotations.SerializedName;



public class Post {
    @SerializedName("ID")
    private  int id;
    @SerializedName("NAME")
    private String name;
    @SerializedName("IMG")
    private String imgUrl;
    @SerializedName("GENDER")
    private String gender;


    public Post(String name, String imgUrl, String gender) {
//        this.id = id;
        this.imgUrl = imgUrl;
        this.gender = gender;
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    @Override
//    public String toString() {
//        return "Post{" +
//                "age='" + imgUrl + '\'' +
//                ", gender='" + gender + '\'' +
//                ", name='" + name + '\'' +
//                '}';
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}