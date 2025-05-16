package com.beykoz.price_comparison_app.UI.Screens.MainScreens.Compare.Views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.beykoz.price_comparison_app.UI.Theme.Green
import com.beykoz.price_comparison_app.UI.Theme.Purple
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import com.beykoz.price_comparison_app.R
import com.beykoz.price_comparison_app.Utils.formatDate

@Composable
fun DetailLineChartView(
    lineDataList: List<List<Double>>,  // Y axis values
    labels: List<String>,  // X axis values
    firstProduct:String = "",
    secondProduct:String = "",
    yAxisLeftEnabled: Boolean = true,
    yAxisRightEnabled: Boolean = true
){
    val context = LocalContext.current

    AndroidView (
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .padding(8.dp),
        factory = { context ->
            LineChart(context).apply {
                description.isEnabled = false
                legend.isEnabled = false
                setTouchEnabled(true)
                setPinchZoom(true)

                // left Y axis(first axis)
                axisLeft.apply {
                    isEnabled = yAxisLeftEnabled
                    textColor = Purple.toArgb()
                    textSize = 12F
                    axisMinimum = 0f
                    typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                }
                // right Y axis(second axis)
                axisRight.apply {
                    isEnabled = yAxisRightEnabled
                    textColor = Green.toArgb()
                    textSize = 12F
                    axisMinimum = 0f
                    typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                }
            } },
        update = { chart ->
            val lineDataSets = lineDataList.mapIndexed { index, yValues ->
                val entries = yValues.mapIndexed { xIndex, y -> Entry(xIndex.toFloat(), y.toFloat()) }
                LineDataSet(entries, "Line ${index + 1}").apply {
                    lineWidth = 2.5f
                    color = if (index == 0 && yAxisLeftEnabled) Purple.toArgb() else Green.toArgb()
                    valueTextColor = Color.Black.toArgb()
                    setDrawCircles(false)
                    setDrawValues(false)
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                    axisDependency = if (index == 0) YAxis.AxisDependency.LEFT else YAxis.AxisDependency.RIGHT
                }
            }
            chart.xAxis.apply {
                textColor = Color.Black.toArgb()
                position = XAxis.XAxisPosition.BOTTOM
                valueFormatter = object : IndexAxisValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        val index = value.toInt()
                        return labels.getOrNull(index)?.split("-")?.let {
                            val month = it[0].padStart(2, '0')
                            val year = it[1].takeLast(2)
                            "$month/$year"
                        } ?: ""
                    }
                }
                yOffset = 10f
            }
            chart.data = LineData(lineDataSets)
            chart.invalidate()
            val mv = CustomMarkerView(context,
                R.layout.marker_view_layout,labels,lineDataList,firstProduct,secondProduct)
            mv.chartView = chart
            chart.marker = mv

        }
    )
}

@SuppressLint("ViewConstructor")
class CustomMarkerView(context: Context?, layoutResource: Int, private val labels: List<String>, private val lineDataList: List<List<Double>>
                       , private val firstStockCode:String, private val secondStockCode:String) : MarkerView(context, layoutResource) {

    @SuppressLint("ResourceType")
    private val tvContent: TextView = findViewById(R.id.marker_text_view)
    override fun refreshContent(e: Entry, highlight: Highlight) {
        val xIndex = e.x.toInt()
        val label = labels.getOrNull(xIndex)?.let { formatDate(it) } ?: "No Date"
        val valueText = StringBuilder()
        valueText.append("Date: $label\n")
        tvContent.setTextColor(Color.White.toArgb())

        lineDataList.forEachIndexed { index, data ->
            val yValue = data.getOrNull(xIndex) ?: 0f
            if (index == 0) {
                valueText.append("${firstStockCode}: ${Utils.formatNumber(yValue.toFloat(), 0, true)}")
            } else {
                valueText.append("\n${secondStockCode}: ${Utils.formatNumber(yValue.toFloat(), 0, true)}")
            }
        }
        tvContent.text = valueText.toString()
        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-(width / 2).toFloat(), -height.toFloat())
    }
}

