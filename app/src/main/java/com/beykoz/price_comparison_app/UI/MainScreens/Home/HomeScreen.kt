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
import com.beykoz.price_comparison_app.UI.MainScreens.Home.Views.PhoneView

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
               Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                   TitleView("Recently Added")
                   PhoneView()
                   TitleView("Most Popular")
                   PhoneView()
                   TitleView("Biggest Price Drops")
                   PhoneView()
                   TitleView("Recently Viewed")
                   PhoneView()
               }
           }
        }
    )
}


