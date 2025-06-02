package com.beykoz.price_comparison_app.Utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


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

fun extractStorageAndRam(input: String): Pair<Double, Double>? {
    val regex = Regex("""(\d+)(?:GB)?\s+(\d+)(?:GB)?""")
    val match = regex.find(input)
    return match?.let {
        val (storage, ram) = it.destructured
        Pair(storage.toDouble(), ram.toDouble())
    }
}

fun convertAntutu(text: String): Double {
    val regex = Regex("""AnTuTu:\s*(\d+)""")
    val match = regex.find(text)
    return match?.groups?.get(1)?.value?.toDoubleOrNull() ?: 0.0
}

@RequiresApi(Build.VERSION_CODES.O)
fun formaterDate(input: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy, MMMM d", Locale.ENGLISH)
    val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")  // istediğin formata göre

    val date = LocalDate.parse(input, inputFormatter)
    return date.format(outputFormatter)
}


