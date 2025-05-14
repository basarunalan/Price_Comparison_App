package com.beykoz.price_comparison_app.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.beykoz.price_comparison_app.Data.Remote.Clients.ComparePageClient
import com.beykoz.price_comparison_app.Data.Remote.Clients.DetailPageClient
import com.beykoz.price_comparison_app.Data.Remote.Models.Compare.CompareProductsResponseModel
import com.beykoz.price_comparison_app.Data.Remote.Models.Detail.DetailPageResponseModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import okhttp3.Response

class ComparePageViewModel : ViewModel() {

    private val _compareProductsData = MutableStateFlow< CompareProductsResponseModel?>(null)
    val compareProductsData: StateFlow<CompareProductsResponseModel?> = _compareProductsData

    private val _firstProductData = MutableStateFlow< DetailPageResponseModel?>(null)
    val firstProductData: StateFlow<DetailPageResponseModel?> = _firstProductData
    private val _secondProductData = MutableStateFlow< DetailPageResponseModel?>(null)
    val secondProductData: StateFlow<DetailPageResponseModel?> = _secondProductData


    suspend fun fetchCompareProductsDetailData(
        firstProductID: String? = null,
        secondProductID: String? = null
    ) {
        withContext(Dispatchers.IO) {
            try {
                val productID = firstProductID ?: secondProductID
                val targetState = if (firstProductID != null) _firstProductData else _secondProductData

                productID?.let {
                    val request = DetailPageClient.getDetailPageData(it)
                    val response = DetailPageClient.client.newCall(request).execute()

                    if (response.isSuccessful) {
                        response.body?.string()?.let { responseBody ->
                            val productData = Gson().fromJson(responseBody, DetailPageResponseModel::class.java)
                            targetState.value = productData
                        }
                    } else {
                        Log.e("Error", "Code: ${response.code}")
                    }
                } ?: Log.e("Error", "Both product IDs are null")

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    suspend fun fetchCompareProductsData() {
        withContext(Dispatchers.IO) {
            try {
                val request = ComparePageClient.getCompareProducts()
                val response: Response = ComparePageClient.client.newCall(request).execute()

                if (response.isSuccessful) {
                    response.body?.string()?.let { responseBody ->
                        val compareProductsResponse = Gson().fromJson(responseBody,
                            CompareProductsResponseModel::class.java)
                        _compareProductsData.value = compareProductsResponse
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