package com.beykoz.price_comparison_app.Data.Remote.Models.Search

class SearchPageResponseModel : ArrayList<SearchPageResponseModelItem>()

data class SearchPageResponseModelItem(
    val antutu_score: String?,
    val chipset: String,
    val image_url: String,
    val `internal`: String,
    val nfc: String,
    val price: String,
    val product_id: String,
    val product_name: String,
    val product_rank: Double,
    val release_date: String,
    val screen_size: String
)