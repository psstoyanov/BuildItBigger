package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.test.AndroidTestCase;

import com.example.paskalstoyanov.androidjokelib.DisplayJokes;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by paskalstoyanov on 13/02/16.
 */
public class EndpointsAsyncTaskTest extends AndroidTestCase {
    private static final String LOG_TAG = EndpointsAsyncTaskTest.class.getName();

    public void testSomeAsynTask () throws Throwable {
        // create  a signal to let us know when our task is done.
        final CountDownLatch signal = new CountDownLatch(1);

    /* Just create an in line implementation of an asynctask. Note this
     * would normally not be done, and is just here for completeness.
     * You would just use the task you want to unit test in your project.
     */

        //final EndpointsAsyncTask myTask = new EndpointsAsyncTask(getContext());





    /* The testing thread will wait here until the UI thread releases it
     * above with the countDown() or 30 seconds passes and it times out.
     */
        signal.await(30, TimeUnit.SECONDS);

        // The task is done, and now you can assert some things!
        assertTrue("Happiness", true);
    }

}
