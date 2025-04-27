package com.beykoz.price_comparison_app.UI.Screens.MainScreens.Favourites.Views

import android.os.Handler
import android.util.Log
import android.widget.Space
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.beykoz.price_comparison_app.Data.Local.UserFavouritesData
import com.beykoz.price_comparison_app.Data.Remote.Models.Favourites.FavouritesResponseModelItem
import com.beykoz.price_comparison_app.R
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Favourites.favouriteList
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Home.Views.PriceWeeklyLineChart
import com.beykoz.price_comparison_app.UI.Theme.Green
import com.beykoz.price_comparison_app.UI.Theme.Pink
import com.beykoz.price_comparison_app.UI.Theme.Red
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@Composable
fun FavouriteProductView(navController: NavController ) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center) {
        if(filteredList.isEmpty()){
            Spacer(modifier = Modifier.height(300.dp))
            Text(text = "No Favourite Product Found",)
        } else {
            filteredList.forEach { item ->
                ContentView(item,navController){
                    UserFavouritesData.addOrRemoveFavourite(item.productID)
                    //filteredList = filteredList.filter { it.productID != item.productID } // gerek yok
                    favouriteList = favouriteList.filter { it.productID != item.productID }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ContentView(
    item: FavouritesResponseModelItem,
    navController: NavController,
    onRemove: (FavouritesResponseModelItem) -> Unit
) {
    val swipeState = rememberSwipeableState(0)
    val sizePx = with(LocalDensity.current) { 70.dp.toPx() }
    val anchors = mapOf(0f to 0, -sizePx to 1) // sola kayma
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .swipeable(
                state = swipeState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
    ) {
        Box(
            modifier = Modifier
                .matchParentSize(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Box(
                modifier = Modifier
                    .size(width = 70.dp, height = 100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Red)
                    .clickable {
                        coroutineScope.launch {
                            onRemove(item)
                            swipeState.animateTo(0) // offset sıfırla
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Sil",
                    tint = Color.White
                )
            }
        }


        Box(
            modifier = Modifier
                .offset { IntOffset(swipeState.offset.value.toInt(), 0) }
                .fillMaxSize()
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .clickable { navController.navigate("DetailScreen/${item.productID}") }
        ) {
            // Mevcut Row içeriğin
            Row {
                ImageView(
                    item.image_url,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.2f)
                        .padding(8.dp))
                Column(verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .weight(0.7f)
                ) {
                    Text(item.name, fontSize = 16.sp,fontWeight = FontWeight.Bold, overflow = TextOverflow.Ellipsis, maxLines = 1)
                    Box(modifier = Modifier.padding(vertical = 4.dp)) {
                        PriceWeeklyLineChart(item.price_changes_percentage.last(),item.price_changes_percentage)
                    }
                }
                Column(verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .weight(0.35f)) {
                    Text("${item.price} ₺", fontSize = 14.sp)
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start){
                        Icon(
                            imageVector = if (item.price_changes_percentage.last() > 0 ) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                            contentDescription = "",
                            modifier = Modifier.size(40.dp),
                            tint = if (item.price_changes_percentage.last() > 0 ) Green else Pink
                        )
                        Text(text = "% ${item.price_changes_percentage.last()}",
                            color = if (item.price_changes_percentage.last() > 0 )Green else Red,fontSize = 16.sp)

                    }
                }
            }
        }
    }
}


@Composable
private fun ImageView(ImagePath:String,modifier: Modifier){
    AsyncImage(
        model = ImagePath,
        placeholder = painterResource(R.drawable.ic_launcher_background),
        contentDescription = "The delasign logo",
        contentScale = ContentScale.Fit,
        error = painterResource(R.drawable.ic_launcher_foreground),
        modifier = modifier
    )
}
