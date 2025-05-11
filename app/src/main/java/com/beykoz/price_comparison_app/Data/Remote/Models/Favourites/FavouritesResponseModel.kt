package com.beykoz.price_comparison_app.Data.Remote.Models.Favourites

class FavouritesResponseModel : ArrayList<FavouritesResponseModelItem>()

data class FavouritesResponseModelItem(
    val daily_return: Double,
    val image_url: String?,
    val model_name: String,
    val price: String,
    val price_changes_percentage: List<Double>,
    val product_code: String
)
