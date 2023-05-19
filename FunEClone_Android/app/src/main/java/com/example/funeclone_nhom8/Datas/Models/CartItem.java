package com.example.funeclone_nhom8.Datas.Models;

import java.io.Serializable;

public class CartItem implements Serializable {
    private int id;
    private int quantity;
    private Product product;

    public CartItem() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
