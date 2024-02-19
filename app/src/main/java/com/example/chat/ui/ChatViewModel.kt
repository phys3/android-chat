package com.example.chat.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chat.data.WebSocketClient
import io.ktor.websocket.DefaultWebSocketSession
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val webSocketClient = WebSocketClient()

    fun startChat(url: String, onMessage: (message: String) -> Unit) {
        webSocketClient.start(url, onMessage)
    }

    fun stopChat() {
        viewModelScope.launch {
            webSocketClient.stop()
        }
    }

    fun sendMessage(message: String) {
        viewModelScope.launch {
            webSocketClient.send(message)
        }
    }
}