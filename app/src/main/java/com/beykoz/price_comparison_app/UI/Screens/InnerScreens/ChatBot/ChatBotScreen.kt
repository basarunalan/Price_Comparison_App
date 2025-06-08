package com.beykoz.price_comparison_app.UI.Screens.InnerScreens.ChatBot

import android.os.Handler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.beykoz.price_comparison_app.UI.Screens.InnerScreens.ChatBot.Views.FilterCommand
import com.beykoz.price_comparison_app.UI.Screens.InnerScreens.ChatBot.Views.WiserTopBar
import com.beykoz.price_comparison_app.UI.Screens.InnerScreens.ChatBot.Views.handleUserMessage
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Search.Views.FilterType
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Search.Views.selectedBrands
import com.beykoz.price_comparison_app.UI.Screens.MainScreens.Search.Views.selectedFilterRanges
import com.beykoz.price_comparison_app.UI.Theme.Green
import kotlin.collections.plus

data class ChatMessage(val sender: String, val text: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatBotPage(navController: NavController) {
    val defaultMessage = ChatMessage("Wiser", "Welcome to Wiser! How can I help you today?")
    var messages by remember { mutableStateOf(listOf<ChatMessage>(defaultMessage)) }

    Scaffold(
        topBar = {
            WiserTopBar(navController)
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .imePadding()
            ) {
                MessagesContent(
                    messages,
                    modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                )
                BottomContent(
                    messages = messages,
                    navController = navController,
                    onMessageSent = { updatedList -> messages = updatedList }
                )
            }
        }
    )
}

@Composable
private fun BottomContent(
    messages: List<ChatMessage>,
    navController: NavController,
    onMessageSent: (List<ChatMessage>) -> Unit
) {
    var userInput by remember { mutableStateOf("") }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(75.dp)
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        TextField(
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer),
            modifier = Modifier.fillMaxSize()
                .padding(end = 4.dp)
                .weight(1f)
                .border(1.dp, MaterialTheme.colorScheme.background, RoundedCornerShape(12.dp)),
            value = userInput,
            onValueChange = { userInput = it },
            placeholder = { Text("Type...") }
        )
        Button(
            modifier = Modifier.fillMaxSize().padding(vertical = 2.dp).weight(0.3f),
            shape = RoundedCornerShape(12.dp),
            onClick = {
            if (userInput.isNotBlank()) {
                val userMessage = ChatMessage("You", userInput)
                val updatedMessages = messages + userMessage
                onMessageSent(updatedMessages)

                handleUserMessage(
                    input = userInput,
                    knownBrands = listOf("Apple", "Samsung", "Xiaomi"),
                    onBotResponse = { response ->
                        onMessageSent(updatedMessages + response)
                    },
                    updateFilters = { command ->
                        when (command) {
                            is FilterCommand.BrandList -> {
                                selectedBrands.clear()
                                selectedBrands.addAll(command.brands)
                                navigateIfNotOnSearch(navController)
                            }

                            is FilterCommand.RangeList -> {
                                command.ranges.forEach { rangeCommand ->
                                    val filterType = when (rangeCommand.filterType.lowercase()) {
                                        "price" -> FilterType.Price
                                        "ram" -> FilterType.Ram
                                        "storage" -> FilterType.Storage
                                        "antutu" -> FilterType.Antutu
                                        "screen" -> FilterType.Screen
                                        else -> null
                                    }
                                    filterType?.let {
                                        selectedFilterRanges = selectedFilterRanges.map { range ->
                                            if (range.filterType == it) range.copy(min = rangeCommand.min, max = rangeCommand.max) else range
                                        }
                                    }
                                }
                                navigateIfNotOnSearch(navController)
                            }
                            is FilterCommand.Range -> TODO()
                        }
                    }
                )
                userInput = ""
            }
        }) {
            Text("Send", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}


@Composable
private fun MessagesContent(messages: List<ChatMessage>, modifier: Modifier){
    val listState = rememberLazyListState()
    LaunchedEffect(messages.size) {
        listState.animateScrollToItem(messages.size - 1)
    }
    LazyColumn(
        state = listState,
        modifier = modifier,
    ) {
        items(messages.size) { index ->
            val isUser = messages[index].sender == "You"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
            ) {
                Box(
                    modifier = Modifier
                        .widthIn(max = 350.dp)
                        .background(
                            color = if (isUser) MaterialTheme.colorScheme.primary else Green,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(12.dp)
                ) {
                    Text(
                        text = messages[index].text,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        textAlign = if (isUser) TextAlign.End else TextAlign.Start
                    )
                }
            }
        }
    }
}

private fun navigateIfNotOnSearch(navController: NavController) {
    Handler().postDelayed({
        if (navController.currentDestination?.route != "Search") {
            navController.navigate("Search")
        }
    }, 200)
}

