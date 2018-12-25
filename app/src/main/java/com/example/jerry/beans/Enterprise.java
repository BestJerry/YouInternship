package com.example.jerry.beans;

import java.io.PrintStream;

/**
 * Created by jerry on 2018/3/26.
 */

public class Enterprise {

    private int id;

    private String phone_number;

    private String password;

    private String name;

    private String homepage;

    private String address;

    private String introduction;

    private String voucher_address;

    private String icon_address;

    private int is_certificate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getVoucher_address() {
        return voucher_address;
    }

    public void setVoucher_address(String voucher_address) {
        this.voucher_address = voucher_address;
    }

    public String getIcon_address() {
        return icon_address;
    }

    public void setIcon_address(String icon_address) {
        this.icon_address = icon_address;
    }

    public int getIs_certificate() {
        return is_certificate;
    }

    public void setIs_certificate(int is_certificate) {
        this.is_certificate = is_certificate;
    }
}
