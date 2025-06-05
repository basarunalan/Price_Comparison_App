package com.beykoz.price_comparison_app.UI.Screens.MainScreens.Search.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beykoz.price_comparison_app.UI.Theme.DarkPurple
import com.beykoz.price_comparison_app.UI.Theme.Green

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSheetView(setFilterState: (Boolean) -> Unit) {
    ModalBottomSheet(
        modifier = Modifier.padding(top = 45.dp),
        shape = RoundedCornerShape(16.dp),
        dragHandle = {
            BottomSheetDefaults.DragHandle(
                color = Green,
                height = 8.dp,
                width = 80.dp
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        onDismissRequest = { setFilterState(false) },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    ){
        Column(modifier = Modifier.fillMaxSize()) {
            SheetContentView()
            Button(
                colors = ButtonDefaults.buttonColors(Green),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                onClick = { setFilterState(false) }) {
                Text("Apply", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Color.Black)
            }
        }
    }
}

@Composable
fun SheetContentView() {
    var expandedButton1 by remember { mutableStateOf(true) }
    var expandedButton2 by remember { mutableStateOf(false) }

    Column(modifier = Modifier.height(700.dp).padding(16.dp).verticalScroll(rememberScrollState())) {
        BrandFilter(listOf("Apple", "Samsung", "Xiaomi"))

        ExpandableButtonView("Sorting Options",expandedButton1,setExpandState = { expandedButton1 = it })
        if (expandedButton1) { SortingOptions() }

        ExpandableButtonView("Filter Options",expandedButton2,setExpandState = { expandedButton2 = it })
        if (expandedButton2) {

            selectedFilterRanges.forEachIndexed { index, filterRange ->
                FilterRangeOptions(
                    filterType = filterRange.filterType,
                    currentRange = filterRange.min..filterRange.max,
                    valueRange = when(filterRange.filterType) {
                        FilterType.Price -> 0f..2000f
                        FilterType.Storage -> 0f..1024f
                        FilterType.Ram -> 0f..24f
                        FilterType.Antutu -> 0f..2000000f
                        FilterType.Screen -> 0f..8f
                    },
                    onFilterChanged = { newRange ->
                        selectedFilterRanges = selectedFilterRanges.toMutableList().also {
                            it[index] = it[index].copy(min = newRange.start, max = newRange.endInclusive)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun ExpandableButtonView(title: String, expandState: Boolean, setExpandState: (Boolean) -> Unit){
    var expandedButton by remember { mutableStateOf(expandState) }
    Button(
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
        modifier = Modifier.height(60.dp).padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = {
        expandedButton = !expandedButton
        setExpandState(expandedButton)}
    )
    {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween)
        {
            Text(title, fontSize = 16.sp)
            Icon(
                imageVector = if (expandedButton) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

var selectedFilterSortingIndex by mutableIntStateOf(0)

@Composable
private fun SortingOptions() {
    val sortingHeaders = listOf(
        "Rank ↓",
        "Rank ↑",
        "Release Date ↑",
        "Release Date ↓",
        "Price: Low to high",
        "Price: High to Low",
        "A to Z"
    )
    sortingHeaders.forEachIndexed  { index,label ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(4.dp))
                .clickable(onClick = {
                    selectedFilterSortingIndex = index
                }),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                color = MaterialTheme.colorScheme.primary,
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(start = 8.dp)
            )
            RadioButton(
                selected = selectedFilterSortingIndex == index,
                onClick = {
                    selectedFilterSortingIndex = index
                },
                colors = RadioButtonDefaults.colors(
                    selectedColor = Green,
                    unselectedColor = Green
                )
            )
        }
    }
}

var selectedBrands = mutableStateListOf("Apple", "Samsung", "Xiaomi")

@Composable
fun BrandFilter(
    brandList: List<String>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        brandList.forEach { brand ->
            val isSelected = selectedBrands.contains(brand)
            val backgroundColor = if (isSelected) Green.copy(0.7f) else MaterialTheme.colorScheme.background

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(16))
                    .background(backgroundColor)
                    .clickable {
                        if (isSelected) {
                            selectedBrands.remove(brand)
                        } else {
                            selectedBrands.add(brand)
                        }
                    }
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = brand,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

