package com.beykoz.price_comparison_app.UI.Screens.MainScreens.Home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.beykoz.price_comparison_app.UI.Common.TopAppBars.MainTopBar.MainTopBar
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Home.Views.HeaderView
import com.beykoz.price_comparison_app.UI.Navigation.NavigationBarView
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Home.Views.MostPopularView
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Home.Views.PriceDropView
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Home.Views.RecentlyAddedView
import com.beykoz.price_comparison_app.UI.Theme.Purple
import com.beykoz.price_comparison_app.ViewModels.HomePageViewModel

@Composable
fun HomeScreen(navController: NavController){
    val homePageViewModel: HomePageViewModel = viewModel()
    val homePageData by homePageViewModel.homePageData.collectAsState()

    Scaffold(
        topBar = { MainTopBar(navController) },
        bottomBar = { NavigationBarView(0,navController) },
        content = { paddingValues ->
            if(homePageData == null){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Purple) }
            } else {
                homePageData?.let { data ->
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState()))
                    {
                        HeaderView(data.contents)
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            RecentlyAddedView(data.recently_added,navController)
                            MostPopularView(data.most_popular,navController)
                            PriceDropView(data.biggest_price_drops,navController)
                            //TitleView("Recently Viewed")
                        }
                    }
                }
            }
        }
    )
}


