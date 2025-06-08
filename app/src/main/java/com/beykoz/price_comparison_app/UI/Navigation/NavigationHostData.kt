package com.beykoz.price_comparison_app.UI.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.beykoz.price_comparison_app.UI.Screens.InnerScreens.ChatBot.ChatBotPage
import com.beykoz.price_comparison_app.UI.Screens.InnerScreens.ProductDetailScreen.ProductDetailScreen
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Compare.CompareScreen
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Favourites.FavouritesScreen
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Home.HomeScreen
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Search.SearchScreen

object NavigationHostData {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun NavigationHost(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "Home") {
            composable("Home") { HomeScreen(navController) }
            composable("Favourites") { FavouritesScreen(navController) }
            composable("DetailScreen/{productID}") { backStackEntry ->
                val productID = backStackEntry.arguments?.getString("productID") ?: "p001"
                ProductDetailScreen(
                    productID = productID,
                    navController = navController
                )
            }
            composable("Compare") { CompareScreen(navController) }
            composable("Search") { SearchScreen(navController) }
            composable("Wiser") { ChatBotPage(navController) }
        }
    }
}
