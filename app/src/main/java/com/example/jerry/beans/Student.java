package com.example.jerry.beans;

import java.io.PrintStream;

import okhttp3.internal.connection.StreamAllocation;

/**
 * Created by jerry on 2018/3/26.
 */

public class Student {

    private int id;

    private String phone_number;

    private String password;

    private String head_pic_address;

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

    public String getHead_pic_address() {
        return head_pic_address;
    }

    public void setHead_pic_address(String head_pic_address) {
        this.head_pic_address = head_pic_address;
    }
}
