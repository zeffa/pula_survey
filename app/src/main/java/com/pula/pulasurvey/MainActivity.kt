package com.pula.pulasurvey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.work.WorkManager
import com.pula.pulasurvey.ui.SurveyViewModel
import com.pula.pulasurvey.ui.navigation.AppNavigator
import com.pula.pulasurvey.ui.theme.PulaSurveyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: SurveyViewModel by viewModels()
    private val workManager by lazy {
        WorkManager.getInstance(applicationContext)
    }

    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PulaSurveyTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    AppNavigator(viewModel = viewModel)
                }
            }
        }
//        viewModel.fetchSurveyQuestions()
        viewModel.createPeriodWorker(WorkManager.getInstance(applicationContext))
    }
}