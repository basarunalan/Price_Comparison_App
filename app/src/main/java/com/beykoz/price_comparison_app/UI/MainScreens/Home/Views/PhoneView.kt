package com.beykoz.price_comparison_app.UI.MainScreens.Home.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.beykoz.price_comparison_app.R
import com.beykoz.price_comparison_app.UI.Theme.Purple

@Composable
fun PhoneView(){
    Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())){
        for(i in 1..3)
            ContentView()
    }
}
@Composable
private fun ContentView(){
    Box(modifier = Modifier
        .size(260.dp,140.dp)
        .padding(end = 12.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(MaterialTheme.colorScheme.surfaceContainer)

    ){
        Row {
            ImageView("https://resim.epey.com/952387/m_apple-iphone-16-pro-max-2.png",
                modifier = Modifier.fillMaxSize().weight(0.3f).padding(8.dp))
            Column(verticalArrangement = Arrangement.SpaceBetween,modifier = Modifier.fillMaxSize().padding(8.dp).weight(0.7f)) {
                Text("iPhone 16 Pro Max", fontSize = 14.sp,fontWeight = FontWeight.Bold)
                Row {
                    DetailAndBarChartView("6.9 Inc",modifier = Modifier.fillMaxWidth().padding(end = 8.dp).weight(0.5f))
                    DetailAndBarChartView("256 GB",modifier = Modifier.fillMaxWidth().padding(end = 8.dp).weight(0.5f))
                }
                Row {
                    DetailAndBarChartView("8 GB",modifier = Modifier.fillMaxWidth().padding(end = 8.dp).weight(0.5f))
                    DetailAndBarChartView("4685 mAh",modifier = Modifier.fillMaxWidth().padding(end = 8.dp).weight(0.5f))
                }
                Text("82.499,00 TL")
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
private fun DetailAndBarChartView(label:String,modifier: Modifier){
    Column(modifier = modifier) {
        Text(label,fontSize = 12.sp)
        ElevatedCard (
            elevation = CardDefaults.elevatedCardElevation(4.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.elevatedCardColors(MaterialTheme.colorScheme.surfaceContainer),
            modifier = Modifier.fillMaxWidth().height(8.dp))
        {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(1.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Purple)
            )
        }
    }
}