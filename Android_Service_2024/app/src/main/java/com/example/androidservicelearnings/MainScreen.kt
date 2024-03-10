package com.example.androidservicelearnings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidservicelearnings.ui.CustomButton

@Composable
fun MainScreen(
    serviceScreen: () -> Unit,
    localBindingScreen: () -> Unit,
    remoteBindingScreen: () -> Unit,
    intentServiceScreen: () -> Unit,
    boundIntentServiceScreen: () -> Unit,
    jobIntentServiceScreen: () -> Unit,
    jobSchedulerScreen: () -> Unit,
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CustomButton("Normal Service") {
            serviceScreen()
        }
        CustomButton("Local binding") {
            localBindingScreen()
        }
        CustomButton("Remote Binding") {
            remoteBindingScreen()
        }
        CustomButton("Intent Service") {
            intentServiceScreen()
        }
        CustomButton("IntentService Binding") {
            boundIntentServiceScreen()
        }
        CustomButton("JobIntent Service") {
            jobIntentServiceScreen()
        }
        CustomButton("Job Scheduler") {
            jobSchedulerScreen()
        }
    }
}