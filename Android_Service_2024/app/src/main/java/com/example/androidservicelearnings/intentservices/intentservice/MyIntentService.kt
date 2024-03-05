package com.example.androidservicelearnings.intentservices.intentservice

import android.app.IntentService
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.androidservicelearnings.MainActivity.Companion.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


class MyIntentService : IntentService("just_a_normal_service") {
    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, Thread.currentThread().id.toString())
        for (i in 1..100) {
            runBlocking { delay(1000) }
            Log.d(TAG, "Service is running $i")
        }
        stopSelf()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.d(TAG, "Service stopped")
        super.onDestroy()
    }
}