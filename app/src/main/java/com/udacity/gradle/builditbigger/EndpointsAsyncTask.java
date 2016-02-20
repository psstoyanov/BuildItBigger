package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.example.paskalstoyanov.myapplication.backend.myJokesApi.MyJokesApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

/**
 * Created by paskalstoyanov on 11/02/16.
 */
class EndpointsAsyncTask extends AsyncTask<Context, Long, EndpointsAsyncTask.WrapperOutput> {

    private final String LOG_TAG = EndpointsAsyncTask.class.getSimpleName();


    private static MyJokesApi myApiService = null;
    private Context context;


    @Override
    protected WrapperOutput doInBackground(Context... params)
    {


        context = params[0];
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(context);
        String myMode = sharedPrefs.getString(
                context.getString(R.string.pref_connection_mode_key),
                context.getString(R.string.pref_mode_emulator));



        if(myApiService == null) {  // Only do this once
            MyJokesApi.Builder builder = new MyJokesApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    // - use the PC/Mac IP address when testing with actual device
                    .setRootUrl(myMode)
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
            WrapperOutput successful = new WrapperOutput(myApiService.getMyJokes().execute().getData(),true);
            return successful;
        } catch (IOException e)
        {
            WrapperOutput failed = new WrapperOutput(e.getMessage(),false);
            return failed;
        }
    }

    @Override
    protected void onPostExecute(WrapperOutput output)
    {
        if(output.getWrapperSuccess())
        {
            MainActivityFragment.ShowJoke_Success(output.getWrapperResult());
        }
        else
        {
            MainActivityFragment.ShowJoke_Error(output.getWrapperResult());
        }
    }

    class WrapperOutput
    {
        String results;
        Boolean success;
        public WrapperOutput( String result, Boolean success ) {
            this.results = result;
            this.success = success;
        }
        public boolean getWrapperSuccess()
        {
            return this.success;
        }
        public String getWrapperResult()
        {
            return this.results;
        }
    }
}
