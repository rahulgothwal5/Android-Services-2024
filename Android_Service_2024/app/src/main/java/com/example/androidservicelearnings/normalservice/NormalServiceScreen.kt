package com.example.androidservicelearnings.normalservice

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.androidservicelearnings.ui.CustomButton

@Composable
fun NormalServiceScreen(
    startService: () -> Unit,
    stopService: () -> Unit
) {

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
    }
}