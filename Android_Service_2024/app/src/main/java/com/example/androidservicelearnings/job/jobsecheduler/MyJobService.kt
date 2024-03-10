package com.example.androidservicelearnings.job.jobsecheduler

import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import com.example.androidservicelearnings.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyJobService:JobService() {
    lateinit var job: Job
    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(MainActivity.TAG, Thread.currentThread().id.toString())
        job = GlobalScope.launch {
            withContext(Dispatchers.IO) {
                for (i in 1..10) {
                    delay(1000)
                    Log.d(MainActivity.TAG, "Service is running $i")
                }
            }
            stopSelf()
        }
        return true;
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        job.cancel()
        Log.d(MainActivity.TAG, "Service stopped")
        return false;
    }
}