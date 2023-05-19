package com.example.funeclone_nhom8.Datas.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Currency implements Serializable {
    private int id;
    @SerializedName("currencyName")
    private String name;

    public Currency(){}

    public Currency(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
