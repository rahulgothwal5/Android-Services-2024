package com.example.androidservicelearnings.localbinderservice

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.androidservicelearnings.ui.CustomButton

@Composable
fun LocalBindingScreenScreen(
    startService: () -> Unit,
    stopService: () -> Unit,
    bindService: () -> Unit,
    unbindService: () -> Unit,
    getBoundText: () -> String
) {

    var randomNo by remember {
        mutableStateOf("0")
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomButton("Start Service") {
            startService()
        }
        CustomButton("Stop Service") {
            stopService()
        }

        Text(text = "Random no is ---> $randomNo")

        CustomButton("Bind Service") {
            bindService()
        }

        CustomButton("Unbind Service") {
            unbindService()
        }

        CustomButton("Get bound text") {
           randomNo = getBoundText()
        }
    }
}






