package com.beykoz.price_comparison_app.UI.Common.TopAppBars.MainTopBar.Views

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.beykoz.price_comparison_app.UI.Theme.DarkPurple
import com.beykoz.price_comparison_app.UI.Theme.Purple
import kotlinx.coroutines.launch

@Composable
fun NavigationIconView(navController: NavController) {
    IconButton(onClick = {navController.popBackStack()}){
        Icon(
            modifier = Modifier.size(30.dp),
            tint = MaterialTheme.colorScheme.primary,
            imageVector = Icons.Filled.Menu,
            contentDescription = "Profile"
        )
    }
}
