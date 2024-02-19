package com.example.chat.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.ktor.websocket.DefaultWebSocketSession
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ChatScreen(chatViewModel: ChatViewModel = viewModel()) {
    var text by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf<String>()) }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = text,
            onValueChange = { newText -> text = newText },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                chatViewModel.sendMessage(text)
                text = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Send")
        }
        Spacer(modifier = Modifier.height(16.dp))
        messages.forEach { message ->
            Text(text = message)
        }
    }

    LaunchedEffect(chatViewModel) {
        chatViewModel.startChat("ws://63e4-86-33-64-119.ngrok-free.app/chat") { message ->
            messages = messages + message
        }
    }
}

