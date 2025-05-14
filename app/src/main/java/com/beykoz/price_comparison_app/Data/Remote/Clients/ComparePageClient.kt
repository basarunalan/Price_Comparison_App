package com.beykoz.price_comparison_app.Data.Remote.Clients

import com.beykoz.price_comparison_app.Utils.EndPoints.getCompareProductsURL
import okhttp3.OkHttpClient
import okhttp3.Request

object ComparePageClient {

    val client: OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }

    fun getCompareProducts(): Request {
        return Request.Builder()
            .url(getCompareProductsURL)
            .build()

    }
}