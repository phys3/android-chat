package com.example.chat.data

import io.ktor.client.*
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.url
import io.ktor.websocket.DefaultWebSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WebSocketClient {
    private val client = HttpClient {
        install(WebSockets)
    }
    var session: DefaultClientWebSocketSession? = null

    fun start(url: String, onMessage: (message: String) -> Unit) {
        GlobalScope.launch(Dispatchers.IO) {
            client.webSocket({
                url(url)
            }) {
                session = this
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    onMessage(frame.readText())
                }
            }
        }
    }

    suspend fun stop() {
        session?.close()
        client.close()
    }

    suspend fun send(message: String) {
        session?.send(Frame.Text(message))
    }
}