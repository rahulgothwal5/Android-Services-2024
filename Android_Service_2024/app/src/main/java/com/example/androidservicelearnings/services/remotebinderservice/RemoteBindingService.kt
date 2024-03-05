package com.example.androidservicelearnings.services.remotebinderservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import android.util.Log
import com.example.androidservicelearnings.MainActivity.Companion.TAG
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random


class RemoteBindingService : Service() {
    private var MIN_RANGE = 1
    private var MAX_RANGE = 100
    private var randomNumber = Random.nextInt(MIN_RANGE, MAX_RANGE)
    private lateinit var job: Job

    inner class RandomNumberRequestHandler : Handler(Looper.myLooper()!!) {
        override fun dispatchMessage(msg: Message) {
            when (msg.what) {
                GET_RANDOM -> {
                    val messageSendRandomNumber = Message.obtain(null, GET_RANDOM)
                    messageSendRandomNumber.arg1 = getRandomNo()
                    try {
                        msg.replyTo.send(messageSendRandomNumber)
                    } catch (e: RemoteException) {
                        Log.i(TAG, "$e.message")
                    }
                }
            }
            super.dispatchMessage(msg)
        }
    }

    private val randomNumberMessenger = Messenger(RandomNumberRequestHandler())

    override fun onBind(intent: Intent?): IBinder? {
        return randomNumberMessenger.binder
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, Thread.currentThread().id.toString())
        job = GlobalScope.launch {
            withContext(Dispatchers.IO) {
                while (true) {
                    randomNumber = Random.nextInt(MIN_RANGE, MAX_RANGE)
                    val currentDateTime = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
                    val formattedDateTime = currentDateTime.format(formatter)
                    Log.d(TAG, "RemoteBinding Generated random no. is $randomNumber at $formattedDateTime")
                    delay(1000)
                }
            }
        }
        return START_STICKY
    }


    override fun onUnbind(intent: Intent?): Boolean {
        stopSelf()
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        job.cancel()
        Log.d(TAG, "Service stopped")
        super.onDestroy()
    }

    fun getRandomNo(): Int {
        return randomNumber
    }

    companion object {
        const val GET_RANDOM = 0
    }
}
