package com.example.androidservicelearnings.job.jobsecheduler

import android.R
import android.annotation.SuppressLint
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
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
import androidx.core.app.JobIntentService.enqueueWork
import com.example.androidservicelearnings.MainActivity
import com.example.androidservicelearnings.intentservices.intentservice.MyIntentService
import com.example.androidservicelearnings.services.normalservice.NormalServiceScreen
import com.example.androidservicelearnings.ui.theme.Android_Service_2024Theme


class JobSchedulerActivity : ComponentActivity() {
    private lateinit var jobScheduler: JobScheduler

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        jobScheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
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
                                text = "Job Intent Service",
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
                        NormalServiceScreen(startService = {
                            startJob()
                        }, stopService = {
                            jobScheduler.cancel(101)
                        })
                    }
                }
            }
        }
    }

    private fun startJob() {
        val componentName = ComponentName(this, MyJobService::class.java)
        val jobInfo = JobInfo.Builder(101, componentName)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_CELLULAR)
            .setPeriodic((15 * 60 * 1000).toLong())
            .setRequiresCharging(false)
            .setPersisted(true)
            .build()
        if (jobScheduler.schedule(jobInfo) == JobScheduler.RESULT_SUCCESS) {
            Log.i(
                MainActivity.TAG,
                "MainActivity thread id: " + Thread.currentThread().id + ", job successfully scheduled"
            )
        } else {
            Log.i(
                MainActivity.TAG,
                "MainActivity thread id: " + Thread.currentThread().id + ", job could not be scheduled"
            )
        }
    }

}