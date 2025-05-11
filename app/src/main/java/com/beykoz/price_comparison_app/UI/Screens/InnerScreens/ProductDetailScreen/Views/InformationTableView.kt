package com.beykoz.price_comparison_app.UI.Screens.InnerScreens.ProductDetailScreen.Views

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.beykoz.price_comparison_app.UI.Common.Tables.TableView
import com.beykoz.price_comparison_app.UI.Common.Titles.TitleView

@Composable
fun InformationTableView(label: String, designInformation: Map<String, String>) {
    Column {
        TitleView(label)
        TableView(
            designInformation.map { (key, value) ->
                Pair(key.uppercase(), value)
            },
            mutableListOf("Parameter", "Value")
        )
    }
}
