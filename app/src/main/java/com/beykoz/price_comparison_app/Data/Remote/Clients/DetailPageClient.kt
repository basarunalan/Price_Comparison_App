package com.beykoz.price_comparison_app.Data.Remote.Clients

import com.beykoz.price_comparison_app.Utils.EndPoints.detailPageURL
import com.beykoz.price_comparison_app.Utils.EndPoints.priceListURL
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
    fun getPriceListData(productID: String): Request {
        return Request.Builder()
            .url("$priceListURL$productID.json")
            .build()
    }
}