package com.pula.pulasurvey.ui.navigation

import androidx.navigation.NavController

sealed class Screen(val route: String) {
    object Welcome: Screen("welcome")
    object Survey: Screen("survey")
}

object PulaNavigation {
    fun navigateTo(navController: NavController, route: String, addToBackstack: Boolean) {
        if (!addToBackstack){ navController.popBackStack() }
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(route) {
                inclusive = !addToBackstack
            }
        }
    }
}