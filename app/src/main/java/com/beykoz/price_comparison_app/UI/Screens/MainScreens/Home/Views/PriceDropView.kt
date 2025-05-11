package com.beykoz.price_comparison_app.UI.Screens.MainScreens.Home.Views

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.beykoz.price_comparison_app.Data.Remote.Models.Home.BiggestPriceDrop
import com.beykoz.price_comparison_app.Data.Remote.Models.Home.RecentlyAdded
import com.beykoz.price_comparison_app.R
import com.beykoz.price_comparison_app.UI.Common.Titles.TitleView
import com.beykoz.price_comparison_app.UI.Theme.Green
import com.beykoz.price_comparison_app.UI.Theme.Pink
import com.beykoz.price_comparison_app.UI.Theme.Red
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DividerProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.PopupProperties

@Composable
fun PriceDropView(itemList: List<BiggestPriceDrop>, navController: NavController){
    TitleView("Biggest Price Drops")
    itemList.forEach { item ->
        ContentView(item,navController)
    }
}


@Composable
private fun ContentView(item: BiggestPriceDrop,navController: NavController){
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .padding(vertical = 8.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(MaterialTheme.colorScheme.surfaceContainer)
        .clickable { navController.navigate("DetailScreen/${item.id}") }

    ){
        Row {
            ImageView(item.image_url,
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
                Text(item.name, fontSize = 16.sp,fontWeight = FontWeight.Bold)
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
                Text(item.price, fontSize = 14.sp)
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

@Composable
fun PriceWeeklyLineChart(weeklyReturn:Double,dataset: List<Double>){
    var min by remember { mutableStateOf(dataset.min())  }
    LineChart(
        minValue = min,
        popupProperties = PopupProperties(false),
        animationDelay = 0,
        indicatorProperties = HorizontalIndicatorProperties(false),
        labelHelperProperties = LabelHelperProperties(false),
        dividerProperties = DividerProperties(false),
        gridProperties = GridProperties(false),
        modifier = Modifier.fillMaxSize(),
        data = remember {
            listOf(
                Line(
                    label = "",
                    values = dataset,
                    color = SolidColor(if(weeklyReturn >= 0) Green else Pink),
                    firstGradientFillColor = (if(weeklyReturn >= 0) Green else Pink).copy(alpha = .4f),
                    secondGradientFillColor = Color.Transparent,
                    strokeAnimationSpec = tween(0, easing = EaseIn),
                    gradientAnimationSpec = tween(0, easing = EaseIn),
                    gradientAnimationDelay = 0,
                    drawStyle = DrawStyle.Stroke(width = 2.dp,),
                )
            )
        },
        animationMode = AnimationMode.Together(delayBuilder = {
            it * 0L
        }),
    )
}