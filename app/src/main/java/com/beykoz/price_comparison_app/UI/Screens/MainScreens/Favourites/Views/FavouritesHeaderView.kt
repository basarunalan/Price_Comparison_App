package com.beykoz.price_comparison_app.UI.Screens.MainScreens.Favourites.Views

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
import androidx.compose.material.icons.filled.FilterList
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
import com.beykoz.price_comparison_app.Data.Remote.Models.Favourites.FavouritesResponseModelItem
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Favourites.favouriteList
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Favourites.selectedSortingIndex
import com.beykoz.price_comparison_app.Utils.convertInt

var sortedList by mutableStateOf(emptyList<FavouritesResponseModelItem>())

@Composable
fun FavouritesHeaderView(filterState: Boolean, setFilterState: (Boolean) -> Unit) {
    var text by remember { mutableStateOf("") }

    sortedList = remember(text, favouriteList, selectedSortingIndex) {
        val sorted = if (text.isNotEmpty()) {
            favouriteList.filter { it.model_name.contains(text, ignoreCase = true) }
        } else {
            favouriteList
        }

        when (selectedSortingIndex) {
            0 -> sorted.sortedByDescending { it.daily_return }
            1 -> sorted.sortedBy { it.daily_return }
            2 -> sorted.sortedBy { convertInt(it.price) }
            3 -> sorted.sortedByDescending { convertInt(it.price) }
            4 -> sorted.sortedBy { it.model_name.uppercase() }
            else -> sorted
        }
    }

    Log.e("sorted", sortedList.toString())


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
                        Text("Search Favourites...", style = TextStyle(color = LightGray))
                    }
                },
            )
            IconButton(
                onClick = { setFilterState(!filterState) },
                modifier = Modifier.size(50.dp).padding(end = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.FilterList,
                    contentDescription = "Geri Dön",
                    modifier = Modifier.size(44.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}