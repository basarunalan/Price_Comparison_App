package com.beykoz.price_comparison_app.UI.Common.TopAppBars.InnerTopBar

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.beykoz.price_comparison_app.UI.Common.TopAppBars.InnerTopBar.Views.ActionIconView
import com.beykoz.price_comparison_app.UI.Common.TopAppBars.MainTopBar.Views.TitleView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InnerTopBar(productID: String, navController: NavController){
    CenterAlignedTopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        navigationIcon = {
            IconButton(onClick = {navController.popBackStack()}){
                Icon(
                    modifier = Modifier.size(25.dp),
                    tint = MaterialTheme.colorScheme.primary,
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = "Profile"
                )
            }
        },
        title = {
            TitleView()
        },
        actions = {
            ActionIconView(productID)
        }
    )
}