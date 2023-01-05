/*
 * Created by Giussep Ricardo on 05/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.core.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.giussepr.mubi.domain.error.NoInternetConnection
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ConnectionInterceptor @Inject constructor(private val context: Context) :
  Interceptor {

  override fun intercept(chain: Interceptor.Chain): Response {
    if (!isConnected()) {
      throw NoInternetConnection()
    }
    return chain.proceed(chain.request())
  }

  private fun isConnected(): Boolean {
    val connectivityManager =
      context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val capabilities: NetworkCapabilities? =
      connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

    return if (capabilities != null) {
      capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) && (
          capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
              capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
              capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
    } else {
      false
    }
  }
}
