package com.beykoz.price_comparison_app.UI.Common.Tables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.seanproctor.datatable.DataColumn
import com.seanproctor.datatable.TableColumnWidth
import com.seanproctor.datatable.material3.DataTable
import kotlin.reflect.KProperty1
import kotlin.reflect.full.primaryConstructor

@Composable
inline fun <reified T : Any> TableView(
    dataList: List<T>?,
    headerTitles: MutableList<String>,
) {
    var selectedRow by remember { mutableStateOf<Int?>(null) }

    val properties = T::class.primaryConstructor?.parameters?.mapNotNull { param ->
        T::class.members.find { it.name == param.name } as? KProperty1<T, *>
    } ?: emptyList()

    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(0.dp),
        colors = CardDefaults.elevatedCardColors(MaterialTheme.colorScheme.surfaceContainerLowest)
    ) {
        Column {
            DataTable(
                footerBackgroundColor = Color.Transparent,
                separator = { HorizontalDivider(thickness = 0.dp, color = Color.Transparent) },
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(3.dp)),
                rowHeight = 40.dp,
                rowBackgroundColor = { index ->
                    if (index % 2 == 0) {
                        MaterialTheme.colorScheme.background
                    } else {
                        MaterialTheme.colorScheme.surfaceContainerLowest
                    }
                },
                headerBackgroundColor = MaterialTheme.colorScheme.surfaceContainer,
                columns = headerTitles.map { header ->
                    DataColumn(width = TableColumnWidth.Flex(1f), alignment = Alignment.Center) {
                        Text(
                            header,
                            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                        )
                    }
                }
            ) {
                dataList?.forEachIndexed { index, data ->
                    row {
                        selectedRow = index
                        properties.forEachIndexed { propertyIndex, property ->
                            cell {
                                Text(
                                    text = property.get(data)?.toString() ?: "",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Normal,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}