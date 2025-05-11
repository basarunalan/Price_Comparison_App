package com.beykoz.price_comparison_app.UI.Screens.InnerScreens.ProductDetailScreen.Views

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.beykoz.price_comparison_app.R
import com.beykoz.price_comparison_app.UI.Common.Titles.TitleView
import androidx.core.net.toUri

@Composable
fun StorePricesView(storeList: Map<String,String>){
    Column {
        TitleView("Other Prices")
        storeList.forEach { item ->
            //PriceButtonView(item.logo,item.price,item.url)
        }
    }
}

@Composable
private fun PriceButtonView(logo: String, price: Double,path: String){
    val context = LocalContext.current
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(vertical = 4.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer,RoundedCornerShape(8.dp))
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, path.toUri())
                context.startActivity(intent)
            }

    )
    {
        ImageView(logo,
            modifier = Modifier.padding(4.dp).weight(0.4f))
        Text("$price ₺", fontSize = 18.sp, fontWeight = FontWeight.Medium,
            modifier = Modifier.fillMaxWidth().weight(0.5f).padding(start = 8.dp))
        Icon(
            imageVector = Icons.Filled.ArrowOutward,
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .padding(end = 12.dp),
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