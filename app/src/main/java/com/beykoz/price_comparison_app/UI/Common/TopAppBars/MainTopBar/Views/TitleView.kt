package com.beykoz.price_comparison_app.UI.Common.TopAppBars.MainTopBar.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.beykoz.price_comparison_app.R

@Composable
fun TitleView(){
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
}