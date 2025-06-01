package com.beykoz.price_comparison_app.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beykoz.price_comparison_app.Data.Remote.Clients.SearchPageClient
import com.beykoz.price_comparison_app.Data.Remote.Models.Search.SearchPageResponseModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Response

class SearchPageViewModel : ViewModel() {

    private val _searchPageData = MutableStateFlow< SearchPageResponseModel?>(null)
    val searchPageData: StateFlow<SearchPageResponseModel?> = _searchPageData

    init {
        viewModelScope.launch {
            fetchSearchPageData()
        }
    }

    private suspend fun fetchSearchPageData() {
        withContext(Dispatchers.IO) {
            try {
                val request = SearchPageClient.getSearchPageData()
                val response: Response = SearchPageClient.client.newCall(request).execute()

                if (response.isSuccessful) {
                    response.body?.string()?.let { responseBody ->
                        val searchPageResponse = Gson().fromJson(responseBody, SearchPageResponseModel::class.java)
                        _searchPageData.value = searchPageResponse
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