package com.example.paskalstoyanov.androidjokelib;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DisplayJokes extends AppCompatActivity {

    public static final String EXTRA_JOKEKEY = "com.example.paskalstoyanov.androidjokelib.EXTRA_JOKEKEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display_jokes);

        Intent intent = getIntent();
        String message = intent.getStringExtra(DisplayJokes.EXTRA_JOKEKEY);
        TextView jokeText = (TextView) findViewById(R.id.joke_textview);
        jokeText.setText(message);
    }
}
