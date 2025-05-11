package com.beykoz.price_comparison_app.Utils

import androidx.compose.runtime.Composable


fun convertDouble(value: String): Double {
    val regex = Regex("""\d+(\.\d+)?""")
    val match = regex.find(value)
    return match?.value?.toDoubleOrNull() ?: 0.0
}

fun convertInt(value: String): Int {
    val regex = Regex("""\d+""") // Sadece tam sayıları yakalar
    val match = regex.find(value)
    return match?.value?.toIntOrNull() ?: 0
}
