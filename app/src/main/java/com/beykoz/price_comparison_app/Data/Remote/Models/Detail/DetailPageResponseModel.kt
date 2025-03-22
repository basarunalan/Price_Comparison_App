package com.beykoz.price_comparison_app.Data.Remote.Models.Detail

data class DetailPageResponseModel(
    val colors: List<color>,
    val image: String,
    val name: String,
    val rating: Int,
    val specifications: Specifications,
    val stores: List<Store>
)

data class color(
    val hex: String,
    val name: String
)

data class Store(
    val logo: String,
    val price: Double,
    val site: String,
    val url: String
)

data class Specifications(
    val battery: List<SubList>,
    val camera: List<SubList>,
    val design: List<SubList>,
    val display: List<SubList>,
    val features: List<SubList>,
    val general_info: List<SubList>,
    val hardware: List<SubList>
)

data class SubList(
    val name: String,
    val value: String
)

