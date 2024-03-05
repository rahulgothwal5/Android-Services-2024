package com.example.androidservicelearnings.localbinderservice

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidservicelearnings.ui.theme.Android_Service_2024Theme

class LocalBindingActivity : ComponentActivity() {

    private lateinit var localBindingIntent: Intent

    var isServiceBound = false

    lateinit var localBindingService: LocalBindingService

    private var serviceConnection: ServiceConnection? = null

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        localBindingIntent = Intent(this, LocalBindingService::class.java)
        setContent {
            Android_Service_2024Theme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = MaterialTheme.colorScheme.primary)
                                .padding(12.dp), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "LocalBinding Service",
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
                        LocalBindingScreenScreen(
                            startService = {

                                if (isServiceBound) {
                                    startService(localBindingIntent)
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Not bound to the service yet",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }, stopService = {
                                if (isServiceBound) {
                                    stopService(localBindingIntent)
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Not bound to the service yet",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            bindService = {
                                bindService()
                            }, unbindService = {
                                unbindService()
                            }, getBoundText = {
                                if (isServiceBound) {
                                    localBindingService.getRandomNo().toString()
                                } else {
                                    Toast.makeText(
                                        this,
                                        "Not bound to the service yet",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    "$$$$$$$"
                                }
                            })
                    }
                }

            }
        }
    }

    private fun bindService() {
        if (serviceConnection == null) {
            serviceConnection = object : ServiceConnection {
                override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                    localBindingService =
                        (service as LocalBindingService.LocalBinder).getService()
                    isServiceBound = true
                }

                override fun onServiceDisconnected(name: ComponentName?) {
                    isServiceBound = false
                }
            }
        }
        bindService(localBindingIntent, serviceConnection!!, BIND_AUTO_CREATE)
        Toast.makeText(
            this,
            "Service Bounded",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun unbindService() {
        if (isServiceBound) {
            unbindService(serviceConnection!!)
            isServiceBound = false
            Toast.makeText(
                this,
                "Service UnBounded",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
