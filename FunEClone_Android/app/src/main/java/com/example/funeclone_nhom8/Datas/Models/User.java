package com.example.funeclone_nhom8.Datas.Models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private int id;
    private String email;
    private String name;
    private String password;
    private String avatar;
    @SerializedName("cover_avatar")
    private String coverAvatar;

    @SerializedName("seller")
    private boolean isSeller;
    private String gender;
    private Date birthDate;
    private String bankAccountHolderName;
    private int bankAccountNumber;
    private String bankIdentifierCode;

    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public boolean isSeller() {
        return isSeller;
    }

    public void setSeller(boolean seller) {
        isSeller = seller;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBankAccountHolderName() {
        return bankAccountHolderName;
    }

    public void setBankAccountHolderName(String bankAccountHolderName) {
        this.bankAccountHolderName = bankAccountHolderName;
    }

    public int getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(int bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankIdentifierCode() {
        return bankIdentifierCode;
    }

    public void setBankIdentifierCode(String bankIdentifierCode) {
        this.bankIdentifierCode = bankIdentifierCode;
    }



    public User(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCoverAvatar() {
        return coverAvatar;
    }

    public void setCoverAvatar(String coverAvatar) {
        this.coverAvatar = coverAvatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", coverAvatar='" + coverAvatar + '\'' +
                '}';
    }
}
