package com.beykoz.price_comparison_app.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beykoz.price_comparison_app.Data.Remote.Clients.HomePageClient
import com.beykoz.price_comparison_app.Data.Remote.Models.Home.HomePageResponseModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Response

class HomePageViewModel : ViewModel() {

    private val _homePageData = MutableStateFlow<HomePageResponseModel?>(null)
    val homePageData: StateFlow<HomePageResponseModel?> = _homePageData

    init {
        viewModelScope.launch {
            fetchHomePageData()
        }
    }

    private suspend fun fetchHomePageData() {
        withContext(Dispatchers.IO) {
            try {
                val request = HomePageClient.getHomePageData()
                val response: Response = HomePageClient.client.newCall(request).execute()

                if (response.isSuccessful) {
                    response.body?.string()?.let { responseBody ->
                        val homePageResponse = Gson().fromJson(responseBody, HomePageResponseModel::class.java)
                        _homePageData.value = homePageResponse
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
