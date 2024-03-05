package com.example.androidservicelearnings.intentservices.boundintentservice

import android.app.IntentService
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.example.androidservicelearnings.MainActivity.Companion.TAG
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random


class MyBoundIntentService : IntentService("MyBoundIntentService") {
    private var MIN_RANGE = 1
    private var MAX_RANGE = 100
    private var randomNumber = Random.nextInt(MIN_RANGE, MAX_RANGE)

    private val binder = LocalBinder()

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }


    inner class LocalBinder : Binder() {
        fun getService(): MyBoundIntentService {
            return this@MyBoundIntentService
        }
    }

    override fun onUnbind(intent: Intent?): Boolean {
        stopSelf()
        return super.onUnbind(intent)
    }

    override fun onHandleIntent(intent: Intent?) {
        Log.d(TAG, Thread.currentThread().id.toString())
        while (true) {
            randomNumber = Random.nextInt(MIN_RANGE, MAX_RANGE)
            val currentDateTime = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
            val formattedDateTime = currentDateTime.format(formatter)
            Log.d(TAG, "Generated random no. is $randomNumber at $formattedDateTime")
            runBlocking { delay(1000) }
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "Service stopped")
        super.onDestroy()
    }

    fun getRandomNo(): Int {
        return randomNumber
    }
}
