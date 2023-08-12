package com.example.messenger.repositories

import com.example.messenger.utils.constants.Constants
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebSocketRepository @Inject constructor() {
    private var webSocket: WebSocket? = null

    fun connect(listener: WebSocketListener,uid: String) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(Constants.WEB_SOCKET_URL)
            .addHeader("uid",uid)
            .build()
        webSocket = client.newWebSocket(request, listener)
    }

    fun disconnect() {
        webSocket?.close(1000, null)
        webSocket = null
    }

    fun send(message: String) {
        webSocket?.send(message)
    }
}