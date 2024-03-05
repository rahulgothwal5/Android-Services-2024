package com.example.remotebindingconsumerapp

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.remotebindingconsumerapp.ui.CustomButton
import com.example.remotebindingconsumerapp.ui.theme.RemoteBindingConsumerAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {

    var isServiceBound = false;
    var randomNumberRequestMessenger: Messenger? = null
    var randomNumberReceiveMessenger: Messenger? = null
    private lateinit var serviceIntent: Intent
    var randomNumber = 0

    inner class RandomNumberReceiveHandler : Handler(Looper.myLooper()!!) {
        override fun handleMessage(message: Message) {
            randomNumber = 0
            when (message.what) {
                GET_RANDOM -> {
                    randomNumber = message.arg1
                    Toast.makeText(this@MainActivity, "$randomNumber", Toast.LENGTH_SHORT).show()
                }
            }
            return super.handleMessage(message)
        }
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            randomNumberRequestMessenger = Messenger(service)
            randomNumberReceiveMessenger = Messenger(RandomNumberReceiveHandler())
            isServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            randomNumberRequestMessenger = null
            randomNumberReceiveMessenger = null
            isServiceBound = false
        }

    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        serviceIntent = Intent()
        serviceIntent.component = ComponentName(
            "com.example.androidservicelearnings",
            "com.example.androidservicelearnings.services.remotebinderservice.RemoteBindingService"
        )
        setContent {
            RemoteBindingConsumerAppTheme {
                // A surface container using the 'background' color from the theme
                val scope = rememberCoroutineScope()

                Scaffold(
                    topBar = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.primary)
                                .padding(12.dp), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Remote Service Consumer",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        var randomNo by remember {
                            mutableStateOf("0")
                        }

                        Column(
                            Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(text = "Random no is ---> $randomNo")

                            CustomButton("Bind Service") {
                                bindService()
                            }

                            CustomButton("Unbind Service") {
                                unBindService()
                            }

                            CustomButton("Get bound text") {
                                scope.launch {
                                    randomNo = getBoundText()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private suspend fun getBoundText(): String {
        if (isServiceBound) {
            withContext(Dispatchers.IO){
                val requestMessage = Message.obtain(null, GET_RANDOM)
                requestMessage.replyTo = randomNumberReceiveMessenger
                try {
                    randomNumberRequestMessenger?.send(requestMessage)
                    delay(1000)
                } catch (e: RemoteException) {
                    Log.e("RECIVER_APP", "${e.message}")
                }
            }
            return randomNumber.toString()
        } else {
            Toast.makeText(
                this,
                "Service is not bound",
                Toast.LENGTH_SHORT
            ).show()
            return "$$$$$$"
        }
    }

    private fun bindService() {
        try {
            val boundStatus = bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE)
            if (boundStatus)
                Toast.makeText(
                    this,
                    "Service Bounded",
                    Toast.LENGTH_SHORT
                ).show()
            else
                Toast.makeText(
                    this,
                    "Service binding failed",
                    Toast.LENGTH_SHORT
                ).show()
        } catch (e: Exception) {
            Log.e("RANDOM_0)", "${e.message}")
        }

    }

    private fun unBindService() {
        if (isServiceBound) {
            unbindService(serviceConnection)
            isServiceBound = false
            Toast.makeText(
                this,
                "Service UnBounded",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object {
        const val GET_RANDOM = 0
    }
}
