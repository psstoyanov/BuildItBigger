package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.paskalstoyanov.androidjokelib.DisplayJokes;
import com.example.paskalstoyanov.myapplication.backend.myJokesApi.MyJokesApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import static android.support.v4.content.res.TypedArrayUtils.getString;

/**
 * Created by paskalstoyanov on 11/02/16.
 */
class EndpointsAsyncTask extends AsyncTask<Context, Long, String> {
    private static MyJokesApi myApiService = null;
    private Context context;


    @Override
    protected String doInBackground(Context... params)
    {


        context = params[0];



        if(myApiService == null) {  // Only do this once
            MyJokesApi.Builder builder = new MyJokesApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    // - use the PC/Mac IP address when testing with actual device
                    .setRootUrl("http://10.0.2.2:8080/_ah/api")
                    // Used ifconfig -a on localhost to find IP address (*Unix system)
                    //.setRootUrl("http://192.168.1.240:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }



        try {
            return myApiService.getMyJokes().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Intent tellJoke = new Intent(context, DisplayJokes.class);
        tellJoke.putExtra(DisplayJokes.EXTRA_JOKEKEY, result);
        //tellJoke.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(tellJoke);
    }
}