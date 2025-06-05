package com.beykoz.price_comparison_app.UI.Common.ModelDrawer

import android.annotation.SuppressLint
import android.os.Handler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.beykoz.price_comparison_app.R
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Search.Views.selectedBrands
import com.beykoz.price_comparison_app.UI.Theme.DarkPurple
import kotlinx.coroutines.launch

var drawerState = DrawerState(initialValue = DrawerValue.Closed)

@Composable
fun ModelDrawer(navController: NavController, screen: @Composable () -> Unit){
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet (modifier = Modifier.width(300.dp), drawerContainerColor = MaterialTheme.colorScheme.primary){
                ProfileView(navController)
            }
        }
    ){
        screen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ProfileView(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceContainer),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.height(120.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomEnd = 32.dp))
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.TopCenter
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = {
                    coroutineScope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }})
                {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        tint = Color.White,
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close"
                    )
                }
                Text("Categories", modifier = Modifier.weight(0.8f).padding(start = 8.dp),
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold)
            }
        }
        Column(modifier = Modifier.padding(top = 16.dp)) {
            CategoryButtonView("https://resim.epey.com/952387/b_apple-iphone-16-pro-max-5.jpg","Apple",navController)
            CategoryButtonView("https://resim.epey.com/1001247/m_samsung-galaxy-s25-plus-19.jpg","Samsung",navController)
            CategoryButtonView("https://resim.epey.com/966543/b_xiaomi-15-ultra-7.png","Xiaomi",navController)
        }
    }
}

@Composable
private fun CategoryButtonView(imagePath: String, label: String,navController: NavController){
    val coroutineScope = rememberCoroutineScope()
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
        .fillMaxWidth()
        .height(150.dp)
        .padding(8.dp)
        .background(MaterialTheme.colorScheme.background, RoundedCornerShape(12.dp))
        .clip(RoundedCornerShape(12.dp))
        .clickable{
            selectedBrands.clear()
            selectedBrands.add(label)
            coroutineScope.launch {
                drawerState.apply {
                    if (isClosed) open() else close()
                }
            }
            Handler().postDelayed({
                if (navController.currentDestination?.route != "Search") {
                    navController.navigate("Search")
                }
            }, 200)

        }
    ){
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically){
            Box (modifier = Modifier.weight(0.7f), contentAlignment = Alignment.Center){
                AsyncImage(
                    model = imagePath,
                    placeholder = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = "The delasign logo",
                    contentScale = ContentScale.Fit,
                    error = painterResource(R.drawable.ic_launcher_foreground),
                    modifier = Modifier.fillMaxSize()
                )
            }
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f).padding(start = 12.dp)
            )
            Icon(
                imageVector = Icons.Filled.ArrowForwardIos,
                contentDescription = "End Icon",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}
