package com.beykoz.price_comparison_app.UI.Screens.InnerScreens.ChatBot.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.beykoz.price_comparison_app.R
import java.nio.file.WatchEvent

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun WiserTopBar(navController: NavController){
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
            Box(
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth().height(100.dp).offset(0.dp,-16.dp),
                    painter = painterResource(id =
                        if(isSystemInDarkTheme()) R.drawable.app_logo_dark
                        else R.drawable.app_logo_light),
                    contentDescription = null
                )
            }
        },
        actions = { Spacer(modifier = Modifier.width(48.dp)) }
    )
}