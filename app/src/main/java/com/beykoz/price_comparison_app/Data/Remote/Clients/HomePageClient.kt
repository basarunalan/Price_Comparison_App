package com.beykoz.price_comparison_app.Data.Remote.Clients

import com.beykoz.price_comparison_app.Utils.EndPoints.homePageURL
import okhttp3.OkHttpClient
import okhttp3.Request

object HomePageClient {

    val client: OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }

    fun getHomePageData(): Request {
        return Request.Builder()
            .url(homePageURL)
            .build()
    }
}