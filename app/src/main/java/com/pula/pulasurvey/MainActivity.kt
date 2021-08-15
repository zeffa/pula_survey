package com.pula.pulasurvey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import com.pula.pulasurvey.databinding.ActivityMainBinding
import com.pula.pulasurvey.ui.navigation.AppNavigator
import com.pula.pulasurvey.ui.theme.PulaSurveyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PulaSurveyTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    AppNavigator()
                }
            }
        }
    }
}