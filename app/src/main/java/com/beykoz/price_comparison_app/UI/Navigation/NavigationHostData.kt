package com.beykoz.price_comparison_app.UI.Navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.beykoz.price_comparison_app.UI.Screens.InnerScreens.ProductDetailScreen.ProductDetailScreen
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Home.HomeScreen
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.deneme
import com.beykoz.price_comparison_app.Utils.EndPoints.detailPageURL

object NavigationHostData {
    @Composable
    fun NavigationHost(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "Home") {
            composable("Home") { HomeScreen(navController) }
            composable("deneme") { deneme(navController) }
            composable("DetailScreen/{productID}") { backStackEntry ->
                val productID = backStackEntry.arguments?.getString("productID")
                val finalProductID = if (productID.isNullOrEmpty()) { "001" } else { productID }
                ProductDetailScreen(finalProductID, navController)
            }
        }
    }
}
