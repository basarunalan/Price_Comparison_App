package com.beykoz.price_comparison_app.UI.Screens.MainScreens.Search.Views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.beykoz.price_comparison_app.Data.Remote.Models.Search.SearchPageResponseModelItem
import com.beykoz.price_comparison_app.R
import com.beykoz.price_comparison_app.UI.Common.Indicators.CustomProgressIndicator
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Home.Views.DetailAndBarChartView
import com.beykoz.price_comparison_app.Utils.convertDouble
import com.beykoz.price_comparison_app.Utils.extractStorageAndRam
import com.beykoz.price_comparison_app.Utils.formaterDate

@SuppressLint("NewApi")
@Composable
fun SearchContentView(item: SearchPageResponseModelItem,navController: NavController){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(140.dp)
        .padding(horizontal = 12.dp, vertical = 8.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(MaterialTheme.colorScheme.surfaceContainer)
        .clickable { navController.navigate("DetailScreen/${item.product_id}") }
    ){
        Row {
            ImageView(item.image_url,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.3f)
                    .padding(8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(5.dp),modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .weight(0.7f)) {
                Text(
                    item.product_name,
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
                        extractStorageAndRam(item.internal)?.first ?:0.0
                        ,maxValue = 1024
                        ,modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp)
                            .weight(0.5f)
                    )
                }
                Row {
                    DetailAndBarChartView(
                        extractStorageAndRam(item.internal)?.second ?:0.0
                        ,maxValue = 24
                        ,modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp)
                            .weight(0.5f))
                    DetailAndBarChartView(
                        (extractStorageAndRam(item.internal)?.first ?:0.0) * 12
                        ,maxValue = 13000
                        ,modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp)
                            .weight(0.5f)
                    )
                }
            }
            Column (modifier = Modifier.fillMaxHeight().padding(end = 8.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceEvenly)
            {
                CustomProgressIndicator(
                    canvasSize = 54.dp,indicatorValue = item.product_rank.toInt(), maxIndicatorValue = 120,
                    backgroundIndicatorStrokeWidth = 20f, foregroundIndicatorStrokeWidth = 20f,
                    backgroundIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
                    foregroundColor = MaterialTheme.colorScheme.primary
                )
                Text("$ ${convertDouble(item.price)}")
                Text(formaterDate(item.release_date), fontSize = 14.sp)
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

