package com.beykoz.price_comparison_app.Data.Remote.Models.Compare

class CompareProductsResponseModel : ArrayList<CompareProductsResponseModelItem>()

data class CompareProductsResponseModelItem(
    val id: String,
    val image_url: String,
    val name: String
)