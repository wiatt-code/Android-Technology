package com.wiatt.simpledemo.myRecycleView;

public class Book {

    private String name;
    private String describe;
    private int imageId;

    public Book(String name, String describe, int imageId) {
        this.name = name;
        this.describe = describe;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
