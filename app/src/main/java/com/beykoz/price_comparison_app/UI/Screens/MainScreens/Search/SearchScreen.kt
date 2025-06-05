package com.beykoz.price_comparison_app.UI.Screens.MainScreens.Search

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.beykoz.price_comparison_app.UI.Common.ModelDrawer.ModelDrawer
import com.beykoz.price_comparison_app.UI.Common.TopAppBars.MainTopBar.MainTopBar
import com.beykoz.price_comparison_app.UI.Navigation.NavigationBarView
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Search.Views.FilterSheetView
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Search.Views.SearchContentView
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Search.Views.SearchHeaderView
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Search.Views.filteredList
import com.beykoz.price_comparison_app.UI.Theme.Purple
import com.beykoz.price_comparison_app.Utils.convertAntutu
import com.beykoz.price_comparison_app.Utils.convertDouble
import com.beykoz.price_comparison_app.ViewModels.SearchPageViewModel

@Composable
fun SearchScreen(navController: NavController){
    val searchPageViewModel: SearchPageViewModel = viewModel()
    val searchPageData by searchPageViewModel.searchPageData.collectAsState()
    var showFilterSheet by remember { mutableStateOf(false) }

    ModelDrawer(navController){
        Scaffold(
            topBar = { MainTopBar(navController) },
            bottomBar = { NavigationBarView(2,navController) },
            content = { paddingValues ->
                if(searchPageData == null){
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Purple)
                    }
                } else {
                    searchPageData?.let { data ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            SearchHeaderView(
                                data,
                                filterState = showFilterSheet,
                                setFilterState = { showFilterSheet = it })
                            Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top)
                            {
                                if(filteredList.isEmpty()){
                                    Column(verticalArrangement = Arrangement.Center, modifier = Modifier.height(600.dp)) {
                                        Text("No Products Found!")
                                    }
                                }
                                else{
                                    for (item in filteredList) {
                                        SearchContentView(item,navController)
                                    }
                                }

                                if (showFilterSheet) {
                                    FilterSheetView(setFilterState = { showFilterSheet = it })
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}

