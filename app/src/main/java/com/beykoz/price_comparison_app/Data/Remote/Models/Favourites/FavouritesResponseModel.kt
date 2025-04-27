package com.beykoz.price_comparison_app.Data.Remote.Models.Favourites

class FavouritesResponseModel : ArrayList<FavouritesResponseModelItem>()

data class FavouritesResponseModelItem(
    val DailyReturn: Double,
    val image_url: String,
    val name: String,
    val price: Int,
    val price_changes_percentage: List<Double>,
    val productID: String
)