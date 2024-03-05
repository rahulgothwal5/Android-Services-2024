package com.example.androidservicelearnings.remotebinderservice

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidservicelearnings.ui.CustomButton
import com.example.androidservicelearnings.ui.theme.Android_Service_2024Theme

class RemoteBindingActivity : ComponentActivity() {

    private lateinit var serviceIntent: Intent

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        serviceIntent = Intent(this, RemoteBindingService::class.java)

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
                                text = "RemoteBinding Service",
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
                        Column(
                            Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CustomButton("Start Service") {
                                startService(serviceIntent)
                            }
                            CustomButton("Stop Service") {
                                stopService(serviceIntent)
                            }
                            CustomButton("Start Consumer App") {
                                val activityIntent = Intent().apply {
                                    setClassName("com.example.remotebindingconsumerapp","com.example.remotebindingconsumerapp.MainActivity")
                                }
                                startActivity(
                                    activityIntent
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
