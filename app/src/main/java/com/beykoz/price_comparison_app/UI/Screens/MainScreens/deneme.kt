package com.beykoz.price_comparison_app.UI.Screens.MainScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.beykoz.price_comparison_app.UI.Common.TopAppBars.MainTopBar.MainTopBar
import com.beykoz.price_comparison_app.UI.Navigation.NavigationBarView

@Composable
fun deneme(navController: NavController){
    Scaffold(
        topBar = { MainTopBar(navController) },
        bottomBar = { NavigationBarView(1,navController) },
        content = { paddingValues ->
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text("Favoriler", fontSize = 24.sp)
            }
        }
    )
}