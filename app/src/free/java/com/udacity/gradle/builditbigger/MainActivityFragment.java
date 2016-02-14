package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.paskalstoyanov.androidjokelib.DisplayJokes;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment
{
    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();


    InterstitialAd mInterstitialAd;
    EndpointsAsyncTask myTask;
    static Context mContext;

    static Button tellJoke;
    static View progressBar;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mContext = getContext();
        tellJoke = (Button) rootView.findViewById(R.id.joke_button);
        progressBar = rootView.findViewById(R.id.progress);
        progressBar.setVisibility(View.INVISIBLE);
        myTask = new EndpointsAsyncTask();

        tellJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded())
                {
                    mInterstitialAd.show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    myTask.execute(mContext);
                    tellJoke.setVisibility(View.INVISIBLE);
                }
            }
        });

        AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        //InterstitialAd
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                new EndpointsAsyncTask().execute(getContext());
            }
        });

        requestNewInterstitial();

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
        Log.v(LOG_TAG, result);
    }


    private void requestNewInterstitial()
    {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

}
