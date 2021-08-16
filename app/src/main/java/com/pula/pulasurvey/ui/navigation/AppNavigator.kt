package com.pula.pulasurvey.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pula.pulasurvey.ui.SurveyViewModel
import com.pula.pulasurvey.ui.screens.SurveyScreen
import com.pula.pulasurvey.ui.screens.WelcomeScreen

@ExperimentalComposeUiApi
@Composable
fun AppNavigator(viewModel: SurveyViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(context) {
                PulaNavigation.navigateTo(navController, Screen.Survey.route, false)
            }
        }

        composable(Screen.Survey.route) {
            SurveyScreen(onFinish = {
                PulaNavigation.navigateTo(navController, Screen.Welcome.route, false)
            })
        }
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun NavPreview() {
    AppNavigator(hiltViewModel())
}