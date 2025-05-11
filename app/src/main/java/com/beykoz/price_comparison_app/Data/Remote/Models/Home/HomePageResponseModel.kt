package com.beykoz.price_comparison_app.Data.Remote.Models.Home

data class HomePageNewResponseModel(
    val biggest_price_drops: List<BiggestPriceDrop>,
    val contents: List<String>,
    val most_popular: List<MostPopular>,
    val recently_added: List<RecentlyAdded>
)

data class BiggestPriceDrop(
    val id: String,
    val image_url: String,
    val name: String,
    val price: String,
    val price_changes_percentage: List<Double>
)

data class RecentlyAdded(
    val id: String,
    val image_url: String?,
    val name: String,
    val price: String,
    val ram: String,
    val screen_size: String,
    val storage: String
)

data class MostPopular(
    val id: String,
    val image_url: String?,
    val name: String,
    val price: String,
    val ram: String,
    val screen_size: String,
    val storage: String
)