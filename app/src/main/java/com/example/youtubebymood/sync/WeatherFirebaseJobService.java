package com.example.youtubebymood.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.RetryStrategy;

public class WeatherFirebaseJobService extends JobService {
    private AsyncTask<Void, Void, Void> mFetchWeatherTask;

    @Override
    public boolean onStartJob(final com.firebase.jobdispatcher.JobParameters job) {
        mFetchWeatherTask = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                Context context = getApplicationContext();
                WeatherSyncTask.syncWeather(context);
                jobFinished(job, false);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(job, false);
            }
        };

        mFetchWeatherTask.execute();
        return true;
    }

    /**
     * Called when the scheduling engine has decided to interrupt the execution of a running job,
     * most likely because the runtime constraints associated with the job are no longer satisfied.
     *
     * @return whether the job should be retried
     * @see Job.Builder#setRetryStrategy(RetryStrategy)
     * @see RetryStrategy
     */
    @Override
    public boolean onStopJob(com.firebase.jobdispatcher.JobParameters job) {
        return false;
    }
}
