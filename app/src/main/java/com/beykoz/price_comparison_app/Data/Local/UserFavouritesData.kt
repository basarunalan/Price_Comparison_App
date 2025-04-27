package com.beykoz.price_comparison_app.Data.Local

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.beykoz.price_comparison_app.Data.Local.UserFavouritesData.sharedPreferences

object UserFavouritesData {

    private const val PREF_NAME = "user_favourites"
    private const val KEY_FAVOURITES = "favourite_list"

    private var sharedPreferences: SharedPreferences? = null

    fun init(context: Context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.applicationContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        }
    }

    fun getFavourites(): List<String> {
        val set = sharedPreferences?.getStringSet(KEY_FAVOURITES, emptySet()) ?: emptySet()
        return set.toList()
    }

    fun addOrRemoveFavourite(productId: String) {
        val current = sharedPreferences?.getStringSet(KEY_FAVOURITES, mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        if (current.contains(productId)) {
            current.remove(productId)
        } else {
            current.add(productId)
        }
        sharedPreferences?.edit { putStringSet(KEY_FAVOURITES, current) }
    }

    fun isFavourite(productId: String): Boolean {
        return sharedPreferences?.getStringSet(KEY_FAVOURITES, emptySet())?.contains(productId) == true
    }
}
