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

    /*
    I had trouble replicating solutions from:
    http://stackoverflow.com/questions/2321829/android-asynctask-testing-with-android-test-framework
    The runTestOnUiThread is called from an Activity.
    This makes the solution more bloated than just testing the AsyncTask itself.
    I went instead with a simpler solution.
    In this stackoverflow thread, the OP needs to get the result from an AsyncTask:
    http://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a
    One of the answers gives a simplistic execution and return statement:

    try {
            String receivedData = new AsyncTask().execute("http://yourdomain.com/yourscript.php").get();
        }
        catch (ExecutionException | InterruptedException ei) {
                ei.printStackTrace();
        }

    It doesn't require a running Activity or taking into account where the test AsyncTask is executed.
    onPostExecute returns a String in the stackoverflow thread matching the one in EndpointsAsyncTask.
    The only thing is to assert that the returned string is not null.
    */

    public void testSomeAsynTask() throws Throwable {

        EndpointsAsyncTask myTask = new EndpointsAsyncTask();
        EndpointsAsyncTask.WrapperOutput result = null;

        try {
            result = myTask.execute(getContext()).get();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Error e) {
            e.printStackTrace();
        }
        assertEquals(true, result.getWrapperSuccess());
        // Test if the joke was correct

        // Currently, the EndpointsAsyncTask() needs alteration for the test to be correct.

        //  .setRootUrl(myMode)  - should use "http://10.0.2.2:8080/_ah/api"
        // onPostExecute   - comment out:
        //             MainActivityFragment.ShowJoke_Success(output.getWrapperResult());
        //            MainActivityFragment.ShowJoke_Error(output.getWrapperResult());
        // respectively.
        // Otherwise it gives, nullPointerException as it tries to access code
        // from MainActivityFragment.




    }
}

