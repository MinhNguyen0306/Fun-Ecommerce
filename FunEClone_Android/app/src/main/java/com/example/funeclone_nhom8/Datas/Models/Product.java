package com.example.funeclone_nhom8.Datas.Models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Product implements Serializable {
    private int id;

    @SerializedName("productName")
    private String name;

    private String description;
    private double pricing;
    private int stock;
    private Currency currency;
    private List<Photo> photos;
    private List<Integer> imageList;
    private int imageResource;

    public int getImageResource() {
        return imageResource;
    }

    public Product(String name, String description, double pricing, int stock) {
        this.name = name;
        this.description = description;
        this.pricing = pricing;
        this.stock = stock;
    }
    public Product(int id, String name, String description, double pricing, int stock, List<Photo> photos) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.pricing = pricing;
        this.stock = stock;
        this.photos = photos;
    }

    public Product(int id, String name, double pricing, int imageResource) {
        this.id = id;
        this.name = name;
        this.pricing = pricing;
        this.imageResource = imageResource;
    }

    public List<Integer> getImageList() {
        return imageList;
    }

    public void setImageList(List<Integer> imageList) {
        this.imageList = imageList;
    }

    public int getTypeDisplay(){
        if(photos != null) {
            if(photos.size() < 2) {
                return 1;
            } else if(photos.size() >= 2 && photos.size() < 4) {
                return 2;
            } else {
                return 4;
            }
        } else {
            return 1;
        }
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPricing() {
        return pricing;
    }

    public void setPricing(double pricing) {
        this.pricing = pricing;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
