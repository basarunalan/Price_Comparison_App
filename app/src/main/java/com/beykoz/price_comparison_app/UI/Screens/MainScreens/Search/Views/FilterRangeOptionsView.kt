package com.beykoz.price_comparison_app.UI.Screens.MainScreens.Search.Views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


data class FilterRange(
    val filterType: FilterType,  // örn: PRICE, STORAGE, RAM
    val min: Float,
    val max: Float
)

enum class FilterType {
    Price, Storage, Ram, Antutu,Screen
}
var selectedFilterRanges by mutableStateOf(
    listOf(
        FilterRange(FilterType.Price, 0f, 2000f),
        FilterRange(FilterType.Storage, 0f, 512f),
        FilterRange(FilterType.Ram, 0f, 16f),
        FilterRange(FilterType.Antutu, 0f, 2000000f),
        FilterRange(FilterType.Screen, 0f, 8f)
    )
)

@Composable
fun FilterRangeOptions(
    filterType: FilterType,
    currentRange: ClosedFloatingPointRange<Float>,
    valueRange: ClosedFloatingPointRange<Float>,
    onFilterChanged: (ClosedFloatingPointRange<Float>) -> Unit
) {
    var sliderPosition by remember { mutableStateOf(currentRange) }
    var minText by remember { mutableStateOf(currentRange.start.toInt().toString()) }
    var maxText by remember { mutableStateOf(currentRange.endInclusive.toInt().toString()) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "${filterType.name} Range", style = MaterialTheme.typography.titleMedium)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "${sliderPosition.start.toInt()}")
            Text(text = "${sliderPosition.endInclusive.toInt()}")
        }

        RangeSlider(
            value = sliderPosition,
            onValueChange = { range ->
                sliderPosition = range
                minText = range.start.toInt().toString()
                maxText = range.endInclusive.toInt().toString()
                onFilterChanged(range)
            },
            valueRange = valueRange,
            steps = 20
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = minText,
                onValueChange = {
                    minText = it.filter { char -> char.isDigit() }
                    val minVal = minText.toFloatOrNull() ?: sliderPosition.start
                    sliderPosition = minVal..sliderPosition.endInclusive
                    onFilterChanged(sliderPosition)
                },
                label = { Text("Min") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(12.dp))

            OutlinedTextField(
                value = maxText,
                onValueChange = {
                    maxText = it.filter { char -> char.isDigit() }
                    val maxVal = maxText.toFloatOrNull() ?: sliderPosition.endInclusive
                    sliderPosition = sliderPosition.start..maxVal
                    onFilterChanged(sliderPosition)
                },
                label = { Text("Max") },
                modifier = Modifier.weight(1f)
            )
        }
    }
}
