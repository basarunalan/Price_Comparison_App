package com.beykoz.price_comparison_app.UI.Screens.MainScreens.Favourites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.beykoz.price_comparison_app.Data.Local.UserFavouritesData
import com.beykoz.price_comparison_app.Data.Remote.Models.Favourites.FavouritesResponseModelItem
import com.beykoz.price_comparison_app.UI.Common.ModelDrawer.ModelDrawer
import com.beykoz.price_comparison_app.UI.Common.TopAppBars.MainTopBar.MainTopBar
import com.beykoz.price_comparison_app.UI.Navigation.NavigationBarView
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Favourites.Views.FavouriteProductView
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Favourites.Views.FavouritesHeaderView
import com.beykoz.price_comparison_app.UI.Theme.Green
import com.beykoz.price_comparison_app.UI.Theme.Purple
import com.beykoz.price_comparison_app.ViewModels.FavouritesPageViewModel

var favouriteList by mutableStateOf(emptyList<FavouritesResponseModelItem>())

@Composable
fun FavouritesScreen(navController: NavController ){
    val favouritesPageViewModel: FavouritesPageViewModel = viewModel()
    val favouritesPageData by favouritesPageViewModel.favouritesData.collectAsState()
    var showSorterSheet by remember { mutableStateOf(false) }

    ModelDrawer(navController){
        Scaffold(
        topBar = { MainTopBar(navController) },
        bottomBar = { NavigationBarView(1,navController) },
        content = { paddingValues ->
            if(favouritesPageData == null){
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Purple)
                }
            } else {
                favouritesPageData?.let { data ->
                    favouriteList = data.filter { it.product_code in UserFavouritesData.getFavourites()}
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(paddingValues)
                    ) {
                         FavouritesHeaderView(
                             filterState = showSorterSheet,
                             setFilterState = { showSorterSheet = it }
                        )
                        FavouriteProductView(navController)
                        if (showSorterSheet) {
                            BottomSheetView(setFilterState = { showSorterSheet = it })
                        }
                        }
                    }
                }
            }
        )
    }
}
var selectedSortingIndex by mutableIntStateOf(-1)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BottomSheetView(setFilterState: (Boolean) -> Unit) {
    val sortingHeaders = listOf(
        "% Change ↑",
        "% Change ↓",
        "Price: Low to high",
        "Price: High to Low",
        "A to Z"
    )

    ModalBottomSheet(
        shape = RoundedCornerShape(16.dp),
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                color = Green,
                height = 8.dp,
                width = 80.dp
            )},
        containerColor = MaterialTheme.colorScheme.primary,
        onDismissRequest = { setFilterState(false) },
        sheetState = rememberModalBottomSheetState(),
    ){
        sortingHeaders.forEachIndexed  { index,label ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp)
                    .clickable(onClick = {
                        selectedSortingIndex = index
                    }),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    color = Color.White,
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
                RadioButton(
                    selected = selectedSortingIndex == index,
                    onClick = {
                        selectedSortingIndex = index
                         },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Green,
                        unselectedColor = Green
                    )
                )
            }
        }
    }
}
