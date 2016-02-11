package com.example.paskalstoyanov.myapplication.backend;

import com.example.JavaJokes;

/**
 * Created by paskalstoyanov on 11/02/16.
 */
public class MyJokes {

    private String myData;

    public String getData() {
        return myData;
    }

    public void setData() { myData = JavaJokes.tellAHandCraftedJoke();}
}
