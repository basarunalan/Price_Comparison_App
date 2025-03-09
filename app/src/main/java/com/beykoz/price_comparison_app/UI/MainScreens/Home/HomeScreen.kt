package com.beykoz.price_comparison_app.UI.MainScreens.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.beykoz.price_comparison_app.UI.Common.Titles.TitleView
import com.beykoz.price_comparison_app.UI.Common.TopAppBars.MainTopBar.MainTopBar
import com.beykoz.price_comparison_app.UI.MainScreens.Home.Views.HeaderView
import com.beykoz.price_comparison_app.UI.Navigation.NavigationBarView
import com.beykoz.price_comparison_app.R
@Composable
fun HomeScreen(navController: NavController){
    Scaffold(
        topBar = { MainTopBar(navController) },
        bottomBar = { NavigationBarView(0,navController) },
        content = { paddingValues ->
           Column(modifier = Modifier
               .fillMaxSize()
               .verticalScroll(rememberScrollState())
               .padding(paddingValues))
           {
               HeaderView()
               Column {
                   Box(modifier = Modifier.size(60.dp)){
                       AsyncImage(
                           model = "https://resim.epey.com/997845/b_tecno-camon-40-pro-1.png",
                           placeholder = painterResource(R.drawable.ic_launcher_background),
                           contentDescription = "The delasign logo",
                           contentScale = ContentScale.Fit,
                           error = painterResource(R.drawable.ic_launcher_foreground),
                           modifier = Modifier.fillMaxSize()
                       )

                   }
                   TitleView("Recently Added")
                   TitleView("Recently Viewed")
                   TitleView("Most Popular")
                   TitleView("Biggest Price Drops")
                   TitleView("Apple Products")
                   TitleView("Samsung Products")
               }
           }
        }
    )
}


