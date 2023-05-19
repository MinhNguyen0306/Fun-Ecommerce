package com.example.funeclone_nhom8.Datas.Models;

import java.io.Serializable;

public class Payment implements Serializable {
    private int id;
    private int creditCardNumber;

    public Payment() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(int creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }
}
