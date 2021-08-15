package com.pula.pulasurvey.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SurveyScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Survey here")
    }
}

@Composable
@Preview(showBackground = true)
fun SurveyPreview() {
    SurveyScreen()
}