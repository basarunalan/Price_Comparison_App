package com.beykoz.price_comparison_app.UI.Screens.MainScreens.Search.Views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.beykoz.price_comparison_app.Data.Remote.Models.Search.SearchPageResponseModelItem
import com.beykoz.price_comparison_app.Utils.convertAntutu
import com.beykoz.price_comparison_app.Utils.convertDouble
import com.beykoz.price_comparison_app.Utils.convertInt
import com.beykoz.price_comparison_app.Utils.extractStorageAndRam

var filteredList by mutableStateOf(emptyList<SearchPageResponseModelItem>())

@Composable
fun SearchHeaderView(
    searchList: List<SearchPageResponseModelItem>,
    filterState: Boolean, setFilterState: (Boolean) -> Unit
){
    var text by remember { mutableStateOf("") }

    filteredList = remember(text, searchList, selectedFilterSortingIndex, selectedFilterRanges) {
        var filtered = if (text.isNotEmpty()) {
            searchList.filter { it.product_name.contains(text, ignoreCase = true) }
        } else {
            searchList
        }

        filtered = when (selectedFilterSortingIndex) {
            0 -> filtered.sortedByDescending { it.product_rank }
            1 -> filtered.sortedBy { it.product_rank }
            2 -> filtered.sortedByDescending { it.release_date }
            3 -> filtered.sortedBy { it.release_date }
            4 -> filtered.sortedBy { convertInt(it.price) }
            5 -> filtered.sortedByDescending { convertInt(it.price) }
            6 -> filtered.sortedBy { it.product_name.uppercase() }
            else -> filtered
        }

        selectedFilterRanges.forEach { filterRange ->
            if (filterRange.min > 0f || filterRange.max < 2000f) {
                filtered = filtered.filter { item ->
                    val value = when (filterRange.filterType) {
                        FilterType.Price -> convertDouble(item.price).toFloat()
                        FilterType.Storage -> extractStorageAndRam(item.internal)?.first?.toFloat() ?: 0f
                        FilterType.Ram -> extractStorageAndRam(item.internal)?.second?.toFloat() ?: 0f
                        FilterType.Antutu -> convertAntutu(item.antutu_score?:"").toFloat()
                        FilterType.Screen -> convertDouble(item.screen_size).toFloat()
                    }
                    value in filterRange.min..filterRange.max
                }
            }
        }
        filtered
    }

    Log.e("filtered", filteredList.toString())


    Box(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(0.dp, 0.dp, 32.dp, 32.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer))
    {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            TextField(
                shape = RoundedCornerShape(20.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer),
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 12.dp)
                    .weight(0.8f)
                    .border(1.dp, MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp)),
                value = text,
                onValueChange = {
                    text = it
                },
                trailingIcon = {
                    Icon(
                        tint = MaterialTheme.colorScheme.primary,
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search") },
                placeholder = {
                    if (text.isEmpty()) {
                        Text("Search...", style = TextStyle(color = LightGray))
                    }
                },
            )
            IconButton(
                onClick = { setFilterState(!filterState) },
                modifier = Modifier.size(50.dp).padding(end = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.FilterAlt,
                    contentDescription = "Geri Dön",
                    modifier = Modifier.size(44.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}