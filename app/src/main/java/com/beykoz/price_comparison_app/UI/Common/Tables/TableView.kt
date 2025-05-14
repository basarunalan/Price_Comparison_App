package com.beykoz.price_comparison_app.UI.Common.Tables

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.reflect.KProperty1
import kotlin.reflect.full.primaryConstructor

@Composable
inline fun <reified T : Any> TableView(
    dataList: List<T>?,
    headerTitles: MutableList<String>,
) {
    val properties = T::class.primaryConstructor?.parameters?.mapNotNull { param ->
        T::class.members.find { it.name == param.name } as? KProperty1<T, *>
    } ?: emptyList()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerLowest,RoundedCornerShape(4.dp))
    ) {
        if (headerTitles.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.surfaceContainer,
                        RoundedCornerShape(4.dp)
                    )
                    .padding(vertical = 12.dp)
            ) {
                headerTitles.forEachIndexed { index, title ->
                    Text(
                        title,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f),
                        style = TextStyle(
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                }
            }
        }
        dataList?.forEachIndexed { index, data ->
            val rowBackground = if (index % 2 == 0)
                MaterialTheme.colorScheme.surfaceContainer
            else
                MaterialTheme.colorScheme.background

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(rowBackground)
                    .padding(vertical = 8.dp)
            ) {
                properties.forEachIndexed { index, property ->
                    val value = property.get(data)
                    Text(
                        text = value?.toString() ?: "",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
        }
    }
}
