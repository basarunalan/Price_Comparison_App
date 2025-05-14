package com.beykoz.price_comparison_app.UI.Screens.MainScreens.Compare

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.beykoz.price_comparison_app.UI.Common.ModelDrawer.ModelDrawer
import com.beykoz.price_comparison_app.UI.Common.Tables.TableView
import com.beykoz.price_comparison_app.UI.Common.Titles.TitleView
import com.beykoz.price_comparison_app.UI.Common.TopAppBars.InnerTopBar.InnerTopBar
import com.beykoz.price_comparison_app.UI.Common.TopAppBars.MainTopBar.MainTopBar
import com.beykoz.price_comparison_app.UI.Navigation.NavigationBarView
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Compare.Views.CompareHeaderView
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Compare.Views.ComparePricesView
import com.beykoz.price_comparison_app.UI.Theme.Purple
import com.beykoz.price_comparison_app.Utils.convertDouble
import com.beykoz.price_comparison_app.ViewModels.ComparePageViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CompareScreen(navController: NavController){

    val comparePageViewModel: ComparePageViewModel = viewModel()
    val firstProductData by comparePageViewModel.firstProductData.collectAsState()
    val secondProductData by comparePageViewModel.secondProductData.collectAsState()

    LaunchedEffect(Unit) {
        comparePageViewModel.fetchCompareProductsDetailData(firstProductID = "p015")
        comparePageViewModel.fetchCompareProductsDetailData(secondProductID = "p002")
    }

    ModelDrawer(navController) {
        Scaffold(
            topBar = { MainTopBar(navController) },
            bottomBar = { NavigationBarView(3,navController) },
            content = { paddingValues ->
                if (firstProductData == null || secondProductData == null) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Purple)
                    }
                } else {
                    firstProductData?.let { firstData ->
                        secondProductData?.let { secondData ->
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(paddingValues)
                                    .verticalScroll(rememberScrollState())
                            )
                            {
                                CompareHeaderView(firstData, secondData)
                                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                                    ComparePricesView(
                                        firstPrice = convertDouble(
                                            firstData.features?.get("price") ?: "0.0"
                                        ),
                                        secondPrice = convertDouble(
                                            secondData.features?.get("price") ?: "0.0"
                                        ),
                                        firstProductName = firstData.model_name,
                                        secondProductName = secondData.model_name
                                    )
                                    InformationTableView(
                                        "Display",
                                        firstData.display ?: emptyMap(),
                                        secondData.display ?: emptyMap(),
                                        firstData.model_name,
                                        secondData.model_name
                                    )
                                    InformationTableView(
                                        "Body",
                                        firstData.body ?: emptyMap(),
                                        secondData.body ?: emptyMap(),
                                        firstData.model_name,
                                        secondData.model_name
                                    )
                                    InformationTableView(
                                        "Features",
                                        firstData.features ?: emptyMap(),
                                        secondData.features ?: emptyMap(),
                                        firstData.model_name,
                                        secondData.model_name
                                    )
                                    InformationTableView(
                                        "Battery",
                                        firstData.battery ?: emptyMap(),
                                        secondData.battery ?: emptyMap(),
                                        firstData.model_name,
                                        secondData.model_name
                                    )
                                    InformationTableView(
                                        "Camera",
                                        firstData.camera ?: emptyMap(),
                                        secondData.camera ?: emptyMap(),
                                        firstData.model_name,
                                        secondData.model_name
                                    )
                                    InformationTableView(
                                        "Tests",
                                        firstData.tests ?: emptyMap(),
                                        secondData.tests ?: emptyMap(),
                                        firstData.model_name,
                                        secondData.model_name
                                    )
                                    InformationTableView(
                                        "Memory",
                                        firstData.memory ?: emptyMap(),
                                        secondData.memory ?: emptyMap(),
                                        firstData.model_name,
                                        secondData.model_name
                                    )
                                    InformationTableView(
                                        "Network",
                                        firstData.network ?: emptyMap(),
                                        secondData.network ?: emptyMap(),
                                        firstData.model_name,
                                        secondData.model_name
                                    )
                                    InformationTableView(
                                        "Platform",
                                        firstData.platform ?: emptyMap(),
                                        secondData.platform ?: emptyMap(),
                                        firstData.model_name,
                                        secondData.model_name
                                    )
                                    InformationTableView(
                                        "Communication",
                                        firstData.comms ?: emptyMap(),
                                        secondData.comms ?: emptyMap(),
                                        firstData.model_name,
                                        secondData.model_name
                                    )
                                    InformationTableView(
                                        "Launch",
                                        firstData.launch ?: emptyMap(),
                                        secondData.launch ?: emptyMap(),
                                        firstData.model_name,
                                        secondData.model_name
                                    )
                                    Spacer(modifier = Modifier.height(40.dp))
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
private fun InformationTableView(
    label: String,
    firstData: Map<String, String>,
    secondData: Map<String, String>,
    firstProductName: String,
    secondProductName: String,
) {
    Column {
        TitleView(label)
        val combinedData = firstData.map { (key, firstValue) ->
            val secondValue = secondData[key] ?: "-"
            Triple(key.uppercase(), firstValue, secondValue)
        }
        TableView(
            combinedData.map { Triple(it.first, it.second, it.third) },
            mutableListOf("", firstProductName, secondProductName)
        )
    }
}


