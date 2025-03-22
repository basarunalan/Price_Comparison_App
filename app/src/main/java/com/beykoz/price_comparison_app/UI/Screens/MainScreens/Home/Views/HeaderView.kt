package com.beykoz.price_comparison_app.UI.Screens.MainScreens.Home.Views

import android.util.Log
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.beykoz.price_comparison_app.R
import com.beykoz.price_comparison_app.UI.Theme.DarkPurple
import com.beykoz.price_comparison_app.UI.Theme.Green
import com.beykoz.price_comparison_app.UI.Theme.Purple
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HeaderView(contentList: List<String>){
    val pagerState = rememberPagerState(pageCount = { contentList.size })

    Box(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(0.dp, 0.dp, 32.dp, 32.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer))

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(-120.dp)) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 32.dp
        ) { index ->
            ElevatedCard (
                elevation = CardDefaults.elevatedCardElevation(3.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .padding(horizontal = 20.dp, vertical = 8.dp))
            {
                AsyncImage(
                    model = contentList[index],
                    placeholder = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = "The delasign logo",
                    contentScale = ContentScale.FillBounds,
                    error = painterResource(R.drawable.ic_launcher_foreground),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Row(
            modifier = Modifier
                .offset(0.dp,130.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            repeat(contentList.size) { iteration ->
                val color = if (pagerState.currentPage == iteration) Green else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(8.dp)
                )
            }
        }
    }
    LaunchedEffect(key1 = pagerState.currentPage) {
        launch {
            delay(3000)
            with(pagerState) {
                val target = if (currentPage < contentList.size - 1) currentPage + 1 else 0
                animateScrollToPage(
                    page = target,
                    animationSpec = tween(
                        durationMillis = 0,
                        easing = FastOutLinearInEasing
                    )
                )
            }
        }
    }
}
