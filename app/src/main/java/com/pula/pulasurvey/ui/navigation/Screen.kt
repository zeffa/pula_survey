package com.pula.pulasurvey.ui.navigation

sealed class Screen(val route: String) {
    object Welcome: Screen("welcome")
    object Survey: Screen("survey")
}