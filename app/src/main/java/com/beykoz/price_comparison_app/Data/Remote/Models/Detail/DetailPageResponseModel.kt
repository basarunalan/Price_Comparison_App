package com.beykoz.price_comparison_app.Data.Remote.Models.Detail

data class DetailPageResponseModel(
    val battery: Map<String, String>?,
    val body: Map<String, String>?,
    val camera: Map<String, String>?,
    val comms: Map<String, String>?,
    val daily_return: Double,
    val display: Map<String, String>?,
    val features: Map<String, String>?,
    val launch: Map<String, String>?,
    val memory: Map<String, String>?,
    val model_name: String,
    val network: Map<String, String>?,
    val platform: Map<String, String>?,
    val product_code: String,
    val rating: Double,
    val tests: Map<String, String>?,
    val updated_at: String,
    val image_url: String?,

)


data class color(
    val hex: String,
    val name: String
)
