package com.beykoz.price_comparison_app.Data.Remote.Clients

import android.util.Log
import com.beykoz.price_comparison_app.Utils.EndPoints.detailPageURL
import com.beykoz.price_comparison_app.Utils.EndPoints.homePageURL
import okhttp3.OkHttpClient
import okhttp3.Request

object DetailPageClient {

    val client: OkHttpClient by lazy {
        OkHttpClient.Builder().build()
    }

    fun getDetailPageData(productID: String): Request {
        return Request.Builder()
            .url("$detailPageURL$productID.json")
            .build()

    }
}