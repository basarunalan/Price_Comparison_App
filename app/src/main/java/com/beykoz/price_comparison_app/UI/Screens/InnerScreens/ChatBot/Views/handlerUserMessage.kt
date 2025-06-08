package com.beykoz.price_comparison_app.UI.Screens.InnerScreens.ChatBot.Views

import android.os.Handler
import androidx.navigation.NavController
import com.beykoz.price_comparison_app.UI.Screens.InnerScreens.ChatBot.ChatMessage


fun handleUserMessage(
    input: String,
    knownBrands: List<String>,
    onBotResponse: (ChatMessage) -> Unit,
    updateFilters: (FilterCommand) -> Unit
) {
    val lowerInput = input.lowercase()

    // Find all matching brands in the input (for multi-brand)
    val matchedBrands = knownBrands.filter { lowerInput.contains(it.lowercase()) }
    if (matchedBrands.isNotEmpty()) {
        updateFilters(FilterCommand.BrandList(matchedBrands))
        onBotResponse(ChatMessage("Wiser", "Filtered by brands: ${matchedBrands.joinToString()}"))
        return
    }


    val enhancedRegexMap = mapOf(
        "price" to listOf(
            Regex("""(?:fiyat)[^\d]*(\d+)[^\d]*(\d+)"""),
            Regex("""en\s+(?:düşük|az)[^\d]*(\d+)"""),
            Regex("""maksimum[^\d]*(\d+)"""),
            Regex("""(\d+)[^\d]*(?:ve\s+üstü|ve\s+fazlası)[^\d]*(?:fiyat)?"""),
            Regex("""(?:fiyat\s+)?aralığı[^\d]*(\d+)[^\d]*(\d+)"""),
            Regex("""price[^\d]*(\d+)[^\d]*(\d+)"""),
            Regex("""minimum[^\d]*(\d+)[^\d]*(?:price)?"""),
            Regex("""max(?:imum)?[^\d]*(\d+)[^\d]*(?:price)?"""),
            Regex("""(\d+)[^\d]*(?:or\s+more|above)[^\d]*(?:price)?""")
        ),
        "ram" to listOf(
            Regex("""ram[^\d]*(\d+)[^\d]*(\d+)"""),
            Regex("""en\s+az\s+ram[^\d]*(\d+)"""),
            Regex("""ram[^\d]*(\d+)"""),
            Regex("""(\d+)[^\d]*gb[^\d]*ram"""),
            Regex("""minimum[^\d]*(\d+)[^\d]*ram"""),
            Regex("""ram[^\d]*(\d+)[^\d]*(?:or\s+more|above)"""),
        ),
        "storage" to listOf(
            Regex("""depola(?:ma)?[^\d]*(\d+)[^\d]*(\d+)"""),
            Regex("""en\s+(?:fazla|yüksek)\s+depola(?:ma)?[^\d]*(\d+)"""),
            Regex("""storage[^\d]*(\d+)[^\d]*(\d+)"""),
            Regex("""maximum[^\d]*(\d+)[^\d]*storage"""),
            Regex("""storage[^\d]*(\d+)[^\d]*(?:or\s+less|under)""")
        ),
        "antutu" to listOf(
            Regex("""antutu[^\d]*(\d+)[^\d]*(\d+)"""),
            Regex("""antutu[^\d]*(\d+)[^\d]*ve\s+(?:üstü|fazlası)"""),
            Regex("""antutu[^\d]*(\d+)[^\d]*(?:or\s+more|above)""")
        ),
        "screen" to listOf(
            Regex("""ekran[^\d]*(\d+)[^\d]*(\d+)"""),
            Regex("""ekran\s+(\d+)[^\d]*inç"""),
            Regex("""ekran[^\d]*(\d+)[^\d]*ve\s+(?:üstü|fazlası)"""),
            Regex("""screen[^\d]*(\d+)[^\d]*(\d+)"""),
            Regex("""screen[^\d]*(\d+)[^\d]*(?:inches)?\s*(?:or\s+more|above)?""")
        )
    )


    val matchedRanges = mutableListOf<FilterCommand.Range>()

    fun String.containsAny(vararg keywords: String): Boolean =
        keywords.any { this.contains(it, ignoreCase = true) }

    for ((key, regexList) in enhancedRegexMap) {
        for (regex in regexList) {
            regex.findAll(lowerInput).forEach { match ->
                val first = match.groupValues.getOrNull(1)?.toFloatOrNull()
                val second = match.groupValues.getOrNull(2)?.toFloatOrNull()

                val (min, max) = when {
                    first != null && second != null -> Pair(first, second)
                    first != null && lowerInput.containsAny("en düşük", "minimum", "min", "at least", "lowest") -> Pair(first, Float.MAX_VALUE)
                    first != null && lowerInput.containsAny("maksimum", "maximum", "max", "at most", "en fazla", "en yüksek") -> Pair(0f, first)
                    first != null && lowerInput.containsAny("üstü", "fazlası", "above", "more", "greater") -> Pair(first, Float.MAX_VALUE)
                    first != null && lowerInput.containsAny("altı", "less", "under", "below", "smaller") -> Pair(0f, first)
                    first != null -> Pair(first, first)
                    else -> return@forEach
                }

                matchedRanges.add(FilterCommand.Range(key, min, max))
            }
        }
    }

    if (matchedRanges.isNotEmpty()) {
        updateFilters(FilterCommand.RangeList(matchedRanges))
        onBotResponse(ChatMessage("Wiser", "Applied filters: " + matchedRanges.joinToString { "${it.filterType} ${it.min}-${it.max}" }))
        return
    }



    // Default fallback
    onBotResponse(ChatMessage("Wiser", "Sorry, I couldn't understand that. Please try something like 'Show Samsung and Apple phones' or 'Price 500-1000'"))
}


sealed class FilterCommand {
    data class BrandList(val brands: List<String>) : FilterCommand()
    data class Range(val filterType: String, val min: Float, val max: Float) : FilterCommand()
    data class RangeList(val ranges: List<Range>) : FilterCommand()
}



