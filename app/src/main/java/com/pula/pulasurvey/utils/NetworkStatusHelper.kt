package com.pula.pulasurvey.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.InetSocketAddress
import java.net.Socket

class NetworkStatusHelper(context: Context) : LiveData<NetworkStatus>() {
    val validConnections: MutableList<Network> = mutableListOf()
    var connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private lateinit var connectivityCallback: ConnectivityManager.NetworkCallback

    override fun onActive() {
        super.onActive()
        connectivityCallback = getConnectivityCallback()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, connectivityCallback)
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(connectivityCallback)
    }

    fun updateNetworkStatus() {
        when {
            validConnections.isNotEmpty() -> postValue(NetworkStatus.Connected)
            else -> postValue(NetworkStatus.Disconnected)
        }
    }

    private fun getConnectivityCallback() = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            val capabilities: NetworkCapabilities? =
                connectivityManager.getNetworkCapabilities(network)
            val hasConnection =
                capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
            if (hasConnection) {
                CoroutineScope(Dispatchers.IO).launch {
                    if (InternetAvailability.isInternetAvailable()) {
                        withContext(Dispatchers.Main) {
                            validConnections.add(network)
                            updateNetworkStatus()
                        }
                    }
                }
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            validConnections.remove(network)
            updateNetworkStatus()
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            when {
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        if (InternetAvailability.isInternetAvailable()) {
                            withContext(Dispatchers.Main) {
                                validConnections.add(network)
                                updateNetworkStatus()
                            }
                        }
                    }
                }
                else -> validConnections.remove(network)
            }
            updateNetworkStatus()
        }
    }
}

object InternetAvailability {
    fun isInternetAvailable(): Boolean {
        return try {
            val socket = Socket()
            socket.connect(InetSocketAddress("8.8.8.8", 53))
            socket.close()
            true
        }catch (e:Exception) {
            false
        }
    }
}