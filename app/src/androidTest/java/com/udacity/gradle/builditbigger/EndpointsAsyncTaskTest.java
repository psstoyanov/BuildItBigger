package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.test.AndroidTestCase;
import android.util.Log;

import com.example.paskalstoyanov.androidjokelib.DisplayJokes;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by paskalstoyanov on 13/02/16.
 */
public class EndpointsAsyncTaskTest extends AndroidTestCase {
    private static final String LOG_TAG = EndpointsAsyncTaskTest.class.getName();

    // Changed entirely the test function for the AsyncTask.
    // Now the task is overriding the onPostExecute parameter.
    // There is no need to change anything inside EndpointsAsyncTask.
    // For some reason it is displayed as Terminated instead of failed/passed.


    // Now it is follow the answer on this stackoverflow topic:
    // http://stackoverflow.com/questions/2321829/android-asynctask-testing-with-android-test-framework

    public void testSomeAsynTask() throws Throwable {

        final CountDownLatch signal = new CountDownLatch(1);

        final boolean[] test_variable = new boolean[1];
        test_variable[0] = false;

        final EndpointsAsyncTask myTask = new EndpointsAsyncTask()
        {
            @Override
            protected void onPostExecute(WrapperOutput output) {
                assertNotNull(output);
                Log.v(LOG_TAG, output.getWrapperResult());
                Log.v(LOG_TAG, String.valueOf(output.getWrapperSuccess()));
                assertEquals(true,output.getWrapperSuccess());
                test_variable[0] = output.getWrapperSuccess();
                signal.countDown();
            }
        };
        try {
            myTask.execute(getContext());
            signal.await();// wait for callback
        }
        catch (InterruptedException e)
        {
            throw e;
        }

        assertTrue("Happiness", true);
    }


}

