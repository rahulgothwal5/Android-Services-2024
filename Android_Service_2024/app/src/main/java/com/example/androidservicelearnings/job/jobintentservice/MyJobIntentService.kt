package com.example.androidservicelearnings.job.jobintentservice

import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import com.example.androidservicelearnings.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


class MyJobIntentService: JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        Log.d(MainActivity.TAG, Thread.currentThread().id.toString())
        for (i in 1..20) {
            if (isStopped) {
                Log.d(MainActivity.TAG,"Service stopped abruptly")
            }
            runBlocking { delay(500) }
            Log.d(MainActivity.TAG, "Service is running $i")
        }
        stopSelf()
    }

    override fun onDestroy() {
        Log.d(MainActivity.TAG, "Service stopped")
        super.onDestroy()
    }

    override fun onStopCurrentWork(): Boolean {
        return true
    }
}