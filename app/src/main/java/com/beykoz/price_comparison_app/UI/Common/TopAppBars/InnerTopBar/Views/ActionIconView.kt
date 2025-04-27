package com.beykoz.price_comparison_app.UI.Common.TopAppBars.InnerTopBar.Views

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beykoz.price_comparison_app.Data.Local.UserFavouritesData

@Composable
fun ActionIconView(productID: String) {
    var isFavourite by remember { mutableStateOf(UserFavouritesData.isFavourite(productID)) }
    IconButton(onClick = {
        isFavourite = !isFavourite
        UserFavouritesData.addOrRemoveFavourite(productID)
    })
    {
       Icon(
           imageVector = if(isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
           contentDescription = "Add",
           modifier = Modifier.size(30.dp),
           tint = MaterialTheme.colorScheme.primary)
    }
}