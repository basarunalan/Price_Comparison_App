package com.beykoz.price_comparison_app.UI.Screens.MainScreens.Compare.Views

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import kotlin.collections.filter
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewModelScope
import com.beykoz.price_comparison_app.Data.Remote.Models.Compare.CompareProductsResponseModel
import com.beykoz.price_comparison_app.Data.Remote.Models.Detail.DetailPageResponseModel
import com.beykoz.price_comparison_app.UI.Theme.Green
import com.beykoz.price_comparison_app.ViewModels.ComparePageViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DropDown(
    color: Color = Green,
    productIndex: Int,
    comparePageViewModel: ComparePageViewModel = viewModel()
) {
    val compareProductsData by comparePageViewModel.compareProductsData.collectAsState()
    val selectedProduct by if (productIndex == 0) {
        comparePageViewModel.firstProductData.collectAsState()
    } else {
        comparePageViewModel.secondProductData.collectAsState()
    }

    LaunchedEffect(Unit) {
        comparePageViewModel.fetchCompareProductsData()
    }
    var isDropdownExpanded by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color)
                .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
                .clickable { isDropdownExpanded = !isDropdownExpanded }
        ) {
            Column {
                DropDownView(selectedProduct)
                if(isDropdownExpanded) {
                    DropDownContent(
                        compareProductsData,
                        isDropdownExpanded = { isDropdownExpanded = it },
                        productIndex
                    )
                }
            }
        }
    }
}

@Composable
private fun DropDownView(compareProductsData: DetailPageResponseModel?,){
    Row(
        modifier = Modifier.fillMaxWidth().height(50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = rememberImagePainter(compareProductsData?.image_url),
            contentDescription = "Logo",
            modifier = Modifier.padding(4.dp).clip(RoundedCornerShape(4.dp)).weight(0.2f,fill = false)
        )
        Text(
            text = compareProductsData?.model_name ?: "",
            color =  Color.White,
            style = TextStyle(fontSize = 12.sp),
            modifier = Modifier.weight(0.3f)
        )
        Icon(
            tint = Color.White,
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = "Dropdown aç/kapat",
            modifier = Modifier.weight(0.1f)
        )
    }
}

@Composable
private fun DropDownContent(
    compareProductsDataList: CompareProductsResponseModel?,
    isDropdownExpanded: (Boolean) -> Unit,
    productIndex:Int,
    comparePageViewModel: ComparePageViewModel = viewModel()
){

    var searchText by remember { mutableStateOf("") }

    val filteredList by remember(searchText) {
        mutableStateOf(
            if (searchText.isNotEmpty()) {
                compareProductsDataList?.filter { it.name.contains(searchText, ignoreCase = true) }
            } else compareProductsDataList
        )
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        TextField(
            modifier = Modifier.padding(10.dp).border(1.dp, LightGray,shape = RoundedCornerShape(20.dp))
                .shadow(elevation = 1.dp, shape = RoundedCornerShape(20.dp)),
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Ara...") },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
            singleLine = true
        )
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 4.dp)
        ) {
            items(filteredList ?: emptyList()) { item ->
                DropdownMenuItem(
                    contentPadding = PaddingValues(0.dp),
                    onClick = {
                        comparePageViewModel.viewModelScope.launch {
                            if(productIndex == 0) comparePageViewModel.fetchCompareProductsDetailData(item.id)
                                else comparePageViewModel.fetchCompareProductsDetailData(secondProductID = item.id)
                        }
                        isDropdownExpanded(false)
                        searchText = ""
                    },
                    text = {
                        Row(
                            modifier = Modifier.fillMaxWidth().height(50.dp).padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberImagePainter(item.image_url),
                                contentDescription = "Logo",
                                modifier = Modifier.clip(RoundedCornerShape(4.dp)).weight(0.1f,fill = false)
                            )
                            Text(text = item.name, color = Color.White,style = TextStyle(fontSize = 12.sp),
                                modifier = Modifier.weight(0.3f).padding(start = 12.dp)
                            )
                        }
                    }
                )
            }
        }
    }
}