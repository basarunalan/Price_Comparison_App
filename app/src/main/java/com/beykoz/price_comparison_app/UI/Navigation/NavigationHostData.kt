package com.beykoz.price_comparison_app.UI.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.beykoz.price_comparison_app.UI.Screens.InnerScreens.ProductDetailScreen.ProductDetailScreen
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Favourites.FavouritesScreen
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Home.HomeScreen

object NavigationHostData {
    @Composable
    fun NavigationHost(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "Home") {
            composable("Home") { HomeScreen(navController) }
            composable("Favourites") { FavouritesScreen(navController) }
            composable("DetailScreen/{productID}") { backStackEntry ->
                val productID = backStackEntry.arguments?.getString("productID")
                val finalProductID = if (productID.isNullOrEmpty()) { "001" } else { productID }
                ProductDetailScreen(finalProductID, navController)
            }
        }
    }
}
