package com.pula.pulasurvey.utils

sealed class NetworkStatus {
    object Connected : NetworkStatus()
    object Disconnected : NetworkStatus()
}