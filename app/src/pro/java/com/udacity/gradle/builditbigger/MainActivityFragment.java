package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.paskalstoyanov.androidjokelib.DisplayJokes;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    EndpointsAsyncTask myTask;
    static Context mContext;

    static Button tellJoke;
    static View progressBar;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mContext = getContext();
        tellJoke = (Button) rootView.findViewById(R.id.joke_button);
        progressBar = rootView.findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);


        tellJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                myTask = new EndpointsAsyncTask();
                myTask.execute(mContext);
                tellJoke.setVisibility(View.INVISIBLE);
            }
        });


        return rootView;
    }

    public static void ShowJoke_Success(String result)
    {
        tellJoke.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        Intent tellJoke = new Intent(mContext, DisplayJokes.class);
        tellJoke.putExtra(DisplayJokes.EXTRA_JOKEKEY, result);
        tellJoke.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mContext.startActivity(tellJoke);

    }

    public static void ShowJoke_Error(String result)
    {
        tellJoke.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }
}
