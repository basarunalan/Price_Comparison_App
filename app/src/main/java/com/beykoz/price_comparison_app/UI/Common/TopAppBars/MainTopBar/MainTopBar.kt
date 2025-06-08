package com.beykoz.price_comparison_app.UI.Common.TopAppBars.MainTopBar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Adb
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.beykoz.price_comparison_app.UI.Common.ModelDrawer.drawerState
import com.beykoz.price_comparison_app.UI.Common.TopAppBars.MainTopBar.Views.NavigationIconView
import com.beykoz.price_comparison_app.UI.Common.TopAppBars.MainTopBar.Views.TitleView
import kotlinx.coroutines.launch

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
            IconButton(onClick = {
                navController.navigate("Wiser")
            }){
                Icon(
                    modifier = Modifier.size(30.dp),
                    tint = MaterialTheme.colorScheme.primary,
                    imageVector = Icons.Filled.Adb,
                    contentDescription = "Wiser"
                )
            }
        }
    )
}
