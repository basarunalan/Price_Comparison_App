package com.beykoz.price_comparison_app.UI.Screens.MainScreens.Home.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.beykoz.price_comparison_app.Data.Remote.Models.Home.MostPopular
import com.beykoz.price_comparison_app.Data.Remote.Models.Home.RecentlyAdded
import com.beykoz.price_comparison_app.R
import com.beykoz.price_comparison_app.UI.Common.Titles.TitleView
import com.beykoz.price_comparison_app.UI.Theme.Green
import com.beykoz.price_comparison_app.UI.Theme.Purple
import com.beykoz.price_comparison_app.Utils.convertDouble
import com.beykoz.price_comparison_app.Utils.encoder

@Composable
fun MostPopularView(itemList: List<MostPopular>, navController: NavController){
    TitleView("Most Popular")
    Row(modifier = Modifier.horizontalScroll(rememberScrollState())){
        itemList.forEach { item ->
            ContentView(item,navController)
        }
    }
}

@Composable
private fun ContentView(item: MostPopular,navController: NavController){
    Box(modifier = Modifier
        .size(260.dp,140.dp)
        .padding(end = 12.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(MaterialTheme.colorScheme.surfaceContainer)
        .clickable { navController.navigate("DetailScreen/${item.id}") }
    ){
        Row {
            ImageView(item.image_url ?: "",
                modifier = Modifier.fillMaxSize().weight(0.3f).padding(8.dp))
            Column(verticalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxSize().padding(8.dp).weight(0.7f)) {
                Text(
                    item.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Row {
                    DetailAndBarChartView(
                        convertDouble(item.screen_size)
                        ,maxValue = 8
                        ,modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp)
                            .weight(0.5f))
                    DetailAndBarChartView(
                        convertDouble(item.storage)
                        ,maxValue = 1024
                        ,modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp)
                            .weight(0.5f)
                    )
                }
                Row {
                    DetailAndBarChartView(
                        convertDouble(item.ram)
                        ,maxValue = 24
                        ,modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp)
                            .weight(0.5f))
                    DetailAndBarChartView(
                        convertDouble(item.ram)
                        ,maxValue = 13000
                        ,modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp)
                            .weight(0.5f)
                    )
                }
                Text(item.price)
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

