package com.example.androidservicelearnings.services.normalservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.androidservicelearnings.MainActivity.Companion.TAG
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MyService : Service() {
    lateinit var job: Job
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, Thread.currentThread().id.toString())
        job = GlobalScope.launch {
            withContext(Dispatchers.IO) {
                for (i in 1..100) {
                    delay(1000)
                    Log.d(TAG, "Service is running $i")
                }
            }
            stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        job.cancel()
        Log.d(TAG, "Service stopped")
        super.onDestroy()
    }
}