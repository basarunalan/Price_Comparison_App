package com.beykoz.price_comparison_app.UI.Screens.MainScreens.Compare.Views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.beykoz.price_comparison_app.UI.Common.Titles.TitleView
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ComparePricesView(firstPrice:Double,secondPrice:Double,firstProductName:String,secondProductName:String){
    val firstDataList = generateSmoothData(firstPrice)
    val secondDataList = generateSmoothData(secondPrice)
    Column {
        TitleView("Price History")
        Box(modifier = Modifier
            .padding(vertical = 4.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer,
                RoundedCornerShape(8.dp)
            )
        ) {
            DetailLineChartView(
                listOf(firstDataList,secondDataList),
                getLast200Days(),
                firstProduct = firstProductName,
                secondProduct = secondProductName
            )
        }
    }
}

private fun generateSmoothData(
    baseValue: Double,
    fluctuation: Double = 10.0,
    count: Int = 200
): List<Double> {
    val dataList = mutableListOf<Double>()
    var currentValue = baseValue

    repeat(count) {
        val delta = (-fluctuation..fluctuation).random()
        currentValue += delta
        dataList.add(currentValue)
    }
    dataList.add(baseValue)

    return dataList
}

// Random range uzantısı
fun ClosedFloatingPointRange<Double>.random() =
    (start + Math.random() * (endInclusive - start))


@RequiresApi(Build.VERSION_CODES.O)
fun getLast200Days(): List<String> {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return (200 downTo 0).map { daysAgo ->
        LocalDate.now().minusDays(daysAgo.toLong()).format(formatter)
    }
}

