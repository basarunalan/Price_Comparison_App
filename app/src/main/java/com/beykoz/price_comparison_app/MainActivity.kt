package com.beykoz.price_comparison_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.beykoz.price_comparison_app.Data.Local.UserFavouritesData
import com.beykoz.price_comparison_app.UI.Navigation.NavigationHostData.NavigationHost
import com.beykoz.price_comparison_app.UI.Theme.Price_Comparison_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UserFavouritesData.init(this)
            Price_Comparison_AppTheme {
                NavigationHost(rememberNavController())
            }
        }
    }
}


