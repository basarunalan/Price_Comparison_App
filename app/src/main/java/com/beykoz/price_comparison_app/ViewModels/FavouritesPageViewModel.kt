package com.beykoz.price_comparison_app.ViewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beykoz.price_comparison_app.Data.Remote.Clients.FavouritesPageClient
import com.beykoz.price_comparison_app.Data.Remote.Models.Favourites.FavouritesResponseModel
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Response

class FavouritesPageViewModel : ViewModel() {
    private val _favouritesData = MutableStateFlow<FavouritesResponseModel?>(null)
    val favouritesData: StateFlow<FavouritesResponseModel?> = _favouritesData

    init {
        viewModelScope.launch {
            fetchFavouritesPageData()
        }
    }

    suspend fun fetchFavouritesPageData() {
        withContext(Dispatchers.IO) {
            try {
                val request = FavouritesPageClient.getFavouritesPageData()
                val response: Response = FavouritesPageClient.client.newCall(request).execute()

                if (response.isSuccessful) {
                    response.body?.string()?.let { responseBody ->
                        val favouritesPageResponse = Gson().fromJson(responseBody,
                            FavouritesResponseModel::class.java)
                        _favouritesData.value = favouritesPageResponse
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