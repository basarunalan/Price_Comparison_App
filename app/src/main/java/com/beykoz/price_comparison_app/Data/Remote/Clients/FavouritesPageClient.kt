package com.beykoz.price_comparison_app.Data.Remote.Clients

import com.beykoz.price_comparison_app.Utils.EndPoints.favouritesPageURL
import okhttp3.OkHttpClient
import okhttp3.Request

object FavouritesPageClient {

    val client: OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }

    fun getFavouritesPageData(): Request {
        return Request.Builder()
            .url(favouritesPageURL)
            .build()
    }
}