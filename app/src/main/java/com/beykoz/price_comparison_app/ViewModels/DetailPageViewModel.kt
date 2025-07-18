package com.beykoz.price_comparison_app.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.beykoz.price_comparison_app.Data.Remote.Clients.DetailPageClient
import com.beykoz.price_comparison_app.Data.Remote.Models.Detail.DetailPageResponseModel
import com.beykoz.price_comparison_app.Data.Remote.Models.Detail.PriceListResponseModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import okhttp3.Response

class DetailPageViewModel : ViewModel() {

    private val _detailPageData = MutableStateFlow<DetailPageResponseModel?>(null)
    val detailPageData: StateFlow<DetailPageResponseModel?> = _detailPageData
    private val _priceListData = MutableStateFlow<PriceListResponseModel?>(null)
    val priceListData: StateFlow<PriceListResponseModel?> = _priceListData


    suspend fun fetchDetailPageData(productID: String) {
        withContext(Dispatchers.IO) {
            try {
                val request = DetailPageClient.getDetailPageData(productID)
                val response: Response = DetailPageClient.client.newCall(request).execute()

                if (response.isSuccessful) {
                    response.body?.string()?.let { responseBody ->
                        val detailPageResponse = Gson().fromJson(responseBody,
                            DetailPageResponseModel::class.java)
                        _detailPageData.value = detailPageResponse
                    }
                } else {
                    Log.e("Error:"," ${response.code}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    suspend fun fetchPriceListData(productID: String) {
        withContext(Dispatchers.IO) {
            try {
                val request = DetailPageClient.getPriceListData(productID)
                val response: Response = DetailPageClient.client.newCall(request).execute()

                if (response.isSuccessful) {
                    response.body?.string()?.let { responseBody ->
                        val priceListResponse = Gson().fromJson(responseBody,
                            PriceListResponseModel::class.java)
                        _priceListData.value = priceListResponse
                    }
                } else {
                    Log.e("Error:"," ${response.code}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}