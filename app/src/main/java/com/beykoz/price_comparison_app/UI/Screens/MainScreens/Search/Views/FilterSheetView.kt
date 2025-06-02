package com.beykoz.price_comparison_app.UI.Screens.MainScreens.Search.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beykoz.price_comparison_app.UI.Theme.Green

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSheetView(setFilterState: (Boolean) -> Unit) {
    ModalBottomSheet(
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
        sheetState = rememberModalBottomSheetState(),
    ){
        SheetContentView()
    }
}

@Composable
fun SheetContentView() {
    var expandedButton1 by remember { mutableStateOf(false) }
    var expandedButton2 by remember { mutableStateOf(false) }

    Column(modifier = Modifier.height(600.dp).padding(16.dp).verticalScroll(rememberScrollState())) {

        ButtonView("Sorting Options",expandedButton1,setExpandState = { expandedButton1 = it })
        if (expandedButton1) { SortingOptions() }

        ButtonView("Filter Options",expandedButton2,setExpandState = { expandedButton2 = it })
        if (expandedButton2) {

            selectedFilterRanges.forEachIndexed { index, filterRange ->
                FilterOptions(
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
private fun ButtonView(title: String, expandState: Boolean, setExpandState: (Boolean) -> Unit){
    var expandedButton by remember { mutableStateOf(expandState) }
    Button(onClick = {
        expandedButton = !expandedButton
        setExpandState(expandedButton)})
    {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(title)
            Icon(
                imageVector = if (expandedButton) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp)
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

