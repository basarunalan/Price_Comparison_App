package com.beykoz.price_comparison_app.UI.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.beykoz.price_comparison_app.UI.MainScreens.Home.HomeScreen
import com.beykoz.price_comparison_app.UI.MainScreens.deneme

object NavigationHostData {
    @Composable
    fun NavigationHost(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "Home") {
            composable("Home") { HomeScreen(navController) }
            composable("deneme") { deneme(navController) }
        }
    }
}
