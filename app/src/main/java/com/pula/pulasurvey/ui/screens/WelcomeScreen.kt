package com.pula.pulasurvey.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.pula.pulasurvey.R

@Composable
fun WelcomeScreen(context: Context, startSurvey: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "PULA SURVEY")
            Text(text = "PULA SURVEY")
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(0.8f),
                onClick = { startSurvey() }
            ) {
                Text(
                    text = context.resources.getString(R.string.start_survey),
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun WelcomeScreenPreview() {
    val context = LocalContext.current
    WelcomeScreen(context, startSurvey = {})
}