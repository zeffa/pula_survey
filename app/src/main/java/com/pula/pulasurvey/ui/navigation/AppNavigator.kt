package com.pula.pulasurvey.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pula.pulasurvey.ui.screens.SurveyScreen
import com.pula.pulasurvey.ui.screens.WelcomeScreen

@ExperimentalComposeUiApi
@Composable
fun AppNavigator() {
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
            SurveyScreen()
        }
    }
}

@ExperimentalComposeUiApi
@Preview
@Composable
fun NavPreview() {
    AppNavigator()
}