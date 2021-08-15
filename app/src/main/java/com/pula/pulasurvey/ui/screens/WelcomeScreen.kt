package com.pula.pulasurvey.ui.screens

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pula.pulasurvey.R
import com.pula.pulasurvey.ui.theme.PulaDarkBlue
import com.pula.pulasurvey.ui.theme.Shapes

@Composable
fun WelcomeScreen(context: Context, startSurvey: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(1.0F)
                .fillMaxWidth()
        ) {
            Text(
                text = context.resources.getString(R.string.pula_survey),
                style = TextStyle(
                    color = PulaDarkBlue,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Box(
            modifier = Modifier
                .weight(1.0F)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = context.resources.getString(R.string.why_survey),
                style = TextStyle(
                    color = PulaDarkBlue,
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
        Box(
            modifier = Modifier
                .weight(1.0F)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .align(Alignment.BottomCenter)
                    .border(color = PulaDarkBlue, width = 1.dp, shape = Shapes.medium),
                onClick = { startSurvey() }
            ) {
                Text(
                    text = context.resources.getString(R.string.start_survey),
                    style = TextStyle(fontWeight = FontWeight.Bold, color = PulaDarkBlue)
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