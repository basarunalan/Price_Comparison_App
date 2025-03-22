package com.beykoz.price_comparison_app.UI.Screens.InnerScreens.ProductDetailScreen.Views

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beykoz.price_comparison_app.UI.Common.Titles.TitleView
import com.beykoz.price_comparison_app.UI.Theme.DarkPurple
import com.beykoz.price_comparison_app.UI.Theme.Green
import com.beykoz.price_comparison_app.UI.Theme.Pink
import ir.ehsannarmani.compose_charts.LineChart
import ir.ehsannarmani.compose_charts.models.AnimationMode
import ir.ehsannarmani.compose_charts.models.DividerProperties
import ir.ehsannarmani.compose_charts.models.DrawStyle
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.IndicatorCount
import ir.ehsannarmani.compose_charts.models.IndicatorPosition
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.Line
import ir.ehsannarmani.compose_charts.models.PopupProperties

@Composable
fun PriceHistoryView(price:Double,dailyReturn:Double){
    val dataList = generateSmoothData(price)
    Column {
        TitleView("Price History")
        Box(modifier = Modifier
            .padding(vertical = 4.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer,
                RoundedCornerShape(8.dp)
            )) {
            LineChartView(dataList, listOf(), dailyReturn)
        }
    }
}

@Composable
private fun LineChartView(prices:List<Double>, dates:List<String>, dailyReturn:Double){
    val color = MaterialTheme.colorScheme.primary
    LineChart(
        minValue = prices.min(),
        popupProperties = PopupProperties(
            true,
            textStyle = TextStyle(fontSize = 16.sp, color = Color.White)
        ),
        animationDelay = 0,
        indicatorProperties = HorizontalIndicatorProperties(
            true,
            textStyle = TextStyle(
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
            position = IndicatorPosition.Horizontal.Start,
            padding = 5.dp,
            count = IndicatorCount.CountBased(10)
        ),
        labelHelperProperties = LabelHelperProperties(false),
        dividerProperties = DividerProperties(false),
        gridProperties = GridProperties(
            yAxisProperties = GridProperties.AxisProperties(lineCount = 6),
            xAxisProperties = GridProperties.AxisProperties(lineCount = 6)
        ),
        /*labelProperties = LabelProperties(
            enabled = true,
            textStyle = TextStyle(fontSize = 10.sp, color = MaterialTheme.colorScheme.onBackground),
            labels = dates[lineIndex].filterIndexed { index, _ -> index % 42 == 0 }.map { date ->
                val parts = date.split("-") // "yyyy-MM-dd" -> ["2024", "02", "19"]
                if (parts.size == 3) "${parts[1]}/${parts[0].takeLast(2)}" else date // "MM/yy"
            }),*/
        modifier = Modifier
            .height(300.dp)
            .padding(10.dp),
        data = remember {
            listOf(
                Line(
                    label = "",
                    values = prices,
                    color = SolidColor(color),
                    firstGradientFillColor = color.copy(
                        alpha = 0.5f
                    ),
                    secondGradientFillColor = Color.Transparent,
                    strokeAnimationSpec = tween(800, easing = EaseIn),
                    gradientAnimationSpec = tween(600, easing = EaseIn),
                    gradientAnimationDelay = 600,
                    drawStyle = DrawStyle.Stroke(width = 2.5.dp)
                )
            )
        },
        animationMode = AnimationMode.Together(delayBuilder = {
            it * 0L
        })
    )
}

fun generateSmoothData(
    baseValue: Double,
    fluctuation: Double = 100.0,
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
