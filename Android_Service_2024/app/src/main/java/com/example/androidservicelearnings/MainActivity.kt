package com.example.androidservicelearnings

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidservicelearnings.intentservices.boundintentservice.IntentServiceBindingActivity
import com.example.androidservicelearnings.intentservices.intentservice.IntentServiceActivity
import com.example.androidservicelearnings.services.localbinderservice.LocalBindingActivity
import com.example.androidservicelearnings.services.normalservice.ServiceActivity
import com.example.androidservicelearnings.services.remotebinderservice.RemoteBindingActivity
import com.example.androidservicelearnings.services.remotebinderservice.RemoteBindingService
import com.example.androidservicelearnings.ui.theme.Android_Service_2024Theme

class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                                text = "Service Demo",
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
                    }
                    MainScreen(serviceScreen = {
                        val activityIntent = Intent(this@MainActivity, ServiceActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        startActivity(
                            activityIntent
                        )
                    }, localBindingScreen = {
                        val activityIntent = Intent(this@MainActivity, LocalBindingActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        startActivity(
                            activityIntent
                        )
                    }, remoteBindingScreen = {
                        val activityIntent = Intent(this@MainActivity, RemoteBindingActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        startActivity(
                            activityIntent
                        )
                    }, intentServiceScreen = {
                        val activityIntent = Intent(this@MainActivity, IntentServiceActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        startActivity(
                            activityIntent
                        )
                    }, boundIntentServiceScreen = {
                        val activityIntent = Intent(this@MainActivity, IntentServiceBindingActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        }
                        startActivity(
                            activityIntent
                        )
                    })
                }

            }
        }
    }


    companion object {
        const val TAG = "SERVICE_TEST"
    }
}

