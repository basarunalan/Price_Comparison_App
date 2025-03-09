package com.beykoz.price_comparison_app.UI.Common.TopAppBars.MainTopBar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.beykoz.price_comparison_app.UI.Common.TopAppBars.MainTopBar.Views.NavigationIconView
import com.beykoz.price_comparison_app.UI.Common.TopAppBars.MainTopBar.Views.TitleView

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainTopBar(navController: NavController) {
    CenterAlignedTopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        navigationIcon = {
            NavigationIconView(navController)
        },
        title = {
            TitleView()
        },
        actions = {
            Spacer(modifier = Modifier.size(50.dp))
        }
    )
}