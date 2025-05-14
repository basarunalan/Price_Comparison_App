package com.beykoz.price_comparison_app.UI.Navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.beykoz.price_comparison_app.UI.Theme.DarkPurple
import com.beykoz.price_comparison_app.UI.Theme.Green
import com.example.bottombar.AnimatedBottomBar
import com.example.bottombar.components.BottomBarItem
import com.example.bottombar.model.IndicatorStyle
import com.example.bottombar.model.ItemStyle
import com.example.bottombar.model.VisibleItem

@Composable
@SuppressLint("StateFlowValueCalledInComposition")
fun NavigationBarView(selectedItem:Int = 0,navController: NavController) {
    var selectedItemIndex by remember { mutableIntStateOf(selectedItem) }

    AnimatedBottomBar(
        Modifier
            .fillMaxWidth()
            .height(70.dp),
            containerShape = RoundedCornerShape(16.dp),
            selectedItem = selectedItem,
            itemSize = items.size,
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            indicatorColor = Green,
            indicatorStyle = IndicatorStyle.LINE
    ) {
        items.forEachIndexed { index, navigationItem ->
            BottomBarItem(
                textColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                iconColor = MaterialTheme.colorScheme.primary,
                selected = selectedItem == index,
                onClick = {
                    if (selectedItem != index) {
                        selectedItemIndex = index
                        when (index) {
                            0 -> navController.navigate("Home")
                            1 -> navController.navigate("Favourites")
                            3 -> navController.navigate("Compare")
                        }
                    }},
                imageVector = navigationItem.second,
                label = navigationItem.first,
                containerColor = Color.Transparent,
                activeIndicatorColor = Green,
                visibleItem = VisibleItem.BOTH,
                itemStyle = ItemStyle.STYLE1
            )
        }
    }
}

private val items = listOf(
    Pair("Home", Icons.Filled.Home),
    Pair("Favourites", Icons.Filled.Favorite),
    Pair("Search", Icons.Filled.Search),
    Pair("Compare", Icons.Filled.CompareArrows),
)
