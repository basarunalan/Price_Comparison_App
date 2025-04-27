package com.beykoz.price_comparison_app.UI.Screens.InnerScreens.ProductDetailScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.beykoz.price_comparison_app.UI.Common.TopAppBars.InnerTopBar.InnerTopBar
import com.beykoz.price_comparison_app.UI.Screens.InnerScreens.ProductDetailScreen.Views.InformationTableView
import com.beykoz.price_comparison_app.UI.Screens.InnerScreens.ProductDetailScreen.Views.DetailHeaderView
import com.beykoz.price_comparison_app.UI.Screens.InnerScreens.ProductDetailScreen.Views.PriceHistoryView
import com.beykoz.price_comparison_app.UI.Screens.InnerScreens.ProductDetailScreen.Views.StorePricesView
import com.beykoz.price_comparison_app.UI.Theme.Purple
import com.beykoz.price_comparison_app.ViewModels.DetailPageViewModel

@Composable
fun ProductDetailScreen(productID:String,navController: NavController){

    val detailPageViewModel: DetailPageViewModel = viewModel()
    val detailPageData by detailPageViewModel.detailPageData.collectAsState()

    LaunchedEffect(productID) {
        detailPageViewModel.fetchDetailPageData(productID)
    }

    Scaffold(
        topBar = { InnerTopBar(productID,navController) },
        content = { paddingValues ->
            if(detailPageData == null){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Purple) }
            } else {
                detailPageData?.let { data ->
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState()))
                    {
                        DetailHeaderView(data)
                        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                            StorePricesView(data.stores)
                            PriceHistoryView(data.stores.first().price,10.0)
                            InformationTableView("Display",data.specifications.display)
                            InformationTableView("Hardware",data.specifications.hardware)
                            InformationTableView("Design",data.specifications.design)
                            InformationTableView("Features",data.specifications.features)
                            InformationTableView("Battery",data.specifications.battery)
                            InformationTableView("Camera",data.specifications.camera)
                            InformationTableView("General Information",data.specifications.general_info)
                            Spacer(modifier = Modifier.height(40.dp))
                        }
                    }
                }
            }
        }
    )
}

