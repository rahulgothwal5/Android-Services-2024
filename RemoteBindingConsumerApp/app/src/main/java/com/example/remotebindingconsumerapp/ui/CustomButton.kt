package com.example.remotebindingconsumerapp.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomButton(label: String, onclick: () -> Unit) {
    Button(modifier = Modifier.fillMaxWidth(.75f), onClick = { onclick() }) {
        Text(text = label)
    }

}