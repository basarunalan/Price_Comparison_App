package com.beykoz.price_comparison_app.Data.Remote.Clients

import com.beykoz.price_comparison_app.Utils.EndPoints.searchPageURL
import okhttp3.OkHttpClient
import okhttp3.Request

object SearchPageClient {

    val client: OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }

    fun getSearchPageData(): Request {
        return Request.Builder()
            .url(searchPageURL)
            .build()
    }
}