package com.beykoz.price_comparison_app.Data.Remote.Models.Home

data class HomePageResponseModel(
    val biggest_price_drops: List<BiggestPriceDrop>,
    val contents: List<String>,
    val most_popular: List<MostPopular>,
    val recently_added: List<RecentlyAdded>
)

data class MostPopular(
    val battery: Int,
    val image_url: String,
    val name: String,
    val price: Double,
    val ram: Int,
    val screen_size: Double,
    val storage: Int
)

data class BiggestPriceDrop(
    val image_url: String,
    val name: String,
    val price: Double,
    val price_changes_percentage: List<Double>
)

data class RecentlyAdded(
    val id: String,
    val battery: Int,
    val image_url: String,
    val name: String,
    val price: Double,
    val ram: Int,
    val screen_size: Double,
    val storage: Int
)