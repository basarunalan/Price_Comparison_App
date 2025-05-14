package com.beykoz.price_comparison_app.Utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(dateString: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("en"))
    val date = LocalDate.parse(dateString, inputFormatter)
    return date.format(outputFormatter)
}
