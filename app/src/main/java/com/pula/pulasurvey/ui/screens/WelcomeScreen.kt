package com.pula.pulasurvey.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WelcomeScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Welcome to Pula")
    }
}