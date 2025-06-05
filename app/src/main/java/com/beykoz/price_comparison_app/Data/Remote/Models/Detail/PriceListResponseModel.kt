package com.beykoz.price_comparison_app.Data.Remote.Models.Detail

data class PriceListResponseModel(
    val product_name: String,
    val stores: List<Store>
)

data class Store(
    val logo: String,
    val price: Double,
    val site: String,
    val url: String
)