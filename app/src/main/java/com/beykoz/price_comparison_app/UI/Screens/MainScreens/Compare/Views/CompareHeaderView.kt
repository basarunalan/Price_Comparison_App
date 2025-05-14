package com.beykoz.price_comparison_app.UI.Screens.MainScreens.Compare.Views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.beykoz.price_comparison_app.Data.Remote.Models.Detail.DetailPageResponseModel
import com.beykoz.price_comparison_app.R
import com.beykoz.price_comparison_app.UI.Common.Indicators.CustomProgressIndicator
import com.beykoz.price_comparison_app.UI.Theme.Green
import com.beykoz.price_comparison_app.ViewModels.ComparePageViewModel

@Composable
fun CompareHeaderView(
    firstData: DetailPageResponseModel,
    secondData: DetailPageResponseModel)
{
    Box(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(0.dp, 0.dp, 32.dp, 32.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer))
    {
        Row {
            ProductColumnView(
                firstData,
                MaterialTheme.colorScheme.primary,
                0,
                modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .weight(0.5f)
            )
            ProductColumnView(
                secondData,
                Green,
                1,
                modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .weight(0.5f)
            )
        }
    }
}

@Composable
private fun ProductColumnView(
    data: DetailPageResponseModel,
    progressColor: Color,
    productIndex: Int,
    modifier: Modifier,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        DropDown(progressColor,productIndex)

        Text(
            data.model_name,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )

        ImageView(
            data.image_url ?: "",
            modifier = Modifier
                .fillMaxSize()
                .weight(0.8f)
                .padding(vertical =  8.dp)
        )

        CustomProgressIndicator(
            canvasSize = 60.dp,
            indicatorValue = data.rating.toInt(),
            maxIndicatorValue = 120,
            backgroundIndicatorStrokeWidth = 20f,
            foregroundIndicatorStrokeWidth = 20f,
            backgroundIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.25f),
            foregroundColor = progressColor
        )
    }
}


@Composable
private fun ImageView(ImagePath:String,modifier: Modifier){
    AsyncImage(
        model = ImagePath,
        placeholder = painterResource(R.drawable.ic_launcher_background),
        contentDescription = "The delasign logo",
        contentScale = ContentScale.Fit,
        error = painterResource(R.drawable.ic_launcher_foreground),
        modifier = modifier
    )
}
