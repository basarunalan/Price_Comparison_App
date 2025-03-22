package com.beykoz.price_comparison_app.UI.Screens.InnerScreens.ProductDetailScreen.Views

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.beykoz.price_comparison_app.Data.Remote.Models.Detail.DetailPageResponseModel
import com.beykoz.price_comparison_app.Data.Remote.Models.Detail.Store
import com.beykoz.price_comparison_app.Data.Remote.Models.Detail.color
import com.beykoz.price_comparison_app.R
import com.beykoz.price_comparison_app.UI.Common.Indicators.CustomProgressIndicator
import com.beykoz.price_comparison_app.UI.Theme.Purple

@Composable
fun DetailHeaderView(data: DetailPageResponseModel) {
    Box(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(0.dp, 0.dp, 32.dp, 32.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer))
    {
        Row {
            ImageView(
                data.image,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.35f)
                    .padding(8.dp)
            )
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .weight(0.7f))
            {
                Row(verticalAlignment = Alignment.CenterVertically)
                {
                    Text(
                        data.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(0.5f),
                        maxLines = 2
                    )
                    CustomProgressIndicator(
                        canvasSize = 60.dp,indicatorValue = data.rating, maxIndicatorValue = 120,
                        backgroundIndicatorStrokeWidth = 20f, foregroundIndicatorStrokeWidth = 20f,
                        backgroundIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
                        foregroundColor = MaterialTheme.colorScheme.primary
                    )
                }

                Row {
                    DetailAndBarChartView(
                        data.specifications.display[0].name,
                        data.specifications.display[0].value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp)
                            .weight(0.5f)
                    )
                    DetailAndBarChartView(
                        data.specifications.hardware[3].name,
                        data.specifications.hardware[3].value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp)
                            .weight(0.5f)
                    )
                }
                Row {
                    DetailAndBarChartView(
                        data.specifications.hardware[2].name,
                        data.specifications.hardware[2].value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp)
                            .weight(0.5f)
                    )
                    DetailAndBarChartView(
                        data.specifications.battery[0].name,
                        data.specifications.battery[0].value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp)
                            .weight(0.5f)
                    )
                }
                ColorsView(data.colors)
                BestPriceButton(data.stores.first())
            }
        }
    }
}

@SuppressLint("UseKtx")
@Composable
private fun ColorsView(colorList:List<color>){
    Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())) {
        colorList.forEach { item ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                .size(90.dp,55.dp)
                .padding(vertical = 12.dp)
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(16.dp)))
            {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.fillMaxSize().weight(0.25f)
                        .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                        .background(Color(android.graphics.Color.parseColor(item.hex)))
                    )
                    Text(item.name,modifier = Modifier.weight(0.6f).padding(start = 8.dp), maxLines = 1, fontSize = 14.sp)
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
private fun BestPriceButton(item: Store) {
    val context = LocalContext.current
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(8.dp))
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, item.url.toUri())
                context.startActivity(intent)
            }
    )
    {
        ImageView(item.logo,
            modifier = Modifier
                .fillMaxSize()
                .weight(0.3f)
                .padding(8.dp))
        Text("${item.price} ₺", fontSize = 18.sp, fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(0.4f))
        Icon(
            imageVector = Icons.Filled.ArrowOutward,
            contentDescription = "",
            modifier = Modifier
                .size(30.dp)
                .padding(end = 4.dp),
            tint = MaterialTheme.colorScheme.primary
        )
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
private fun DetailAndBarChartView(label:String,value:String,modifier: Modifier){
    Column(modifier = modifier,) {
        Column( verticalArrangement = Arrangement.spacedBy(-8.dp)) {
            Text(label,fontSize = 12.sp, color = Color.Gray)
            Text(value,fontSize = 12.sp)
        }
        ElevatedCard (
            elevation = CardDefaults.elevatedCardElevation(1.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(MaterialTheme.colorScheme.background),
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp))
        {
            Box(modifier = Modifier
                .fillMaxHeight()
                .width(70.dp)
                .padding(1.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Purple)
            )
        }
    }
}