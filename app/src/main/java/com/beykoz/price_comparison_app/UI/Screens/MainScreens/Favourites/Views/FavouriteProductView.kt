package com.beykoz.price_comparison_app.UI.Screens.MainScreens.Favourites.Views

import android.widget.Space
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.beykoz.price_comparison_app.Data.Local.UserFavouritesData
import com.beykoz.price_comparison_app.Data.Remote.Models.Favourites.FavouritesResponseModelItem
import com.beykoz.price_comparison_app.R
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Home.Views.PriceWeeklyLineChart
import com.beykoz.price_comparison_app.UI.Theme.Green
import com.beykoz.price_comparison_app.UI.Theme.Pink
import com.beykoz.price_comparison_app.UI.Theme.Red


@Composable
fun FavouriteProductView(navController: NavController ) {

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center) {
        if(filteredList.isEmpty()){
            Spacer(modifier = Modifier.height(300.dp))
            Text(text = "No Favourite Product Found",)
        } else {
            filteredList.forEach { item ->
                SwipeToDismissItem(
                    item = item,
                    onRemove = { removedItem ->
                        UserFavouritesData.addOrRemoveFavourite(item.productID)
                        filteredList = filteredList.filter { it.productID != removedItem.productID }
                    },
                    dismissContent = { favouriteItem ->
                        ContentView(favouriteItem, navController)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeToDismissItem(
    item: FavouritesResponseModelItem,
    onRemove: (FavouritesResponseModelItem) -> Unit,
    dismissContent: @Composable (FavouritesResponseModelItem) -> Unit
) {
    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToStart) {
                onRemove(item)
            }
            false
        }
    )

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        dismissThresholds = { FractionalThreshold(0.2f) },
        background = {
            val color by animateColorAsState(
                targetValue = if (dismissState.targetValue == DismissValue.DismissedToStart) Color.Red else Color.Transparent
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp)
                    .background(color),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White,
                    modifier = Modifier.padding(end = 24.dp)
                )
            }
        },
        dismissContent = {
            dismissContent(item)
        }
    )
}


@Composable
private fun ContentView(item: FavouritesResponseModelItem,navController: NavController){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .padding(vertical = 8.dp, horizontal = 16.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(MaterialTheme.colorScheme.surfaceContainer)
        .clickable { navController.navigate("DetailScreen/${item.productID}") }

    ){
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
                    //PriceWeeklyLineChart(item.price_changes_percentage.last(),item.price_changes_percentage)
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
