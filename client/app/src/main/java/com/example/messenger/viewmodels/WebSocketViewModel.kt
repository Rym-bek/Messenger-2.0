package com.example.messenger.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.messenger.models.Message
import com.example.messenger.models.OnlineStatus
import com.example.messenger.repositories.WebSocketRepository
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class WebSocketViewModel @Inject constructor(
    val webSocketRepository: WebSocketRepository
): ViewModel() {
    private val gson: Gson = Gson()
    val message = MutableLiveData<Message?>()
    val onlineStatus = MutableLiveData<OnlineStatus?>()
    private val webSocketListener = object : WebSocketListener() {
        override fun onMessage(webSocket: WebSocket, text: String) {
            val jsonObject = JSONObject(text)
            if (jsonObject.has("message")) {
                //сообщение
                val chat = gson.fromJson(text, Message::class.java)
                message.postValue(chat)
            }
            else if (jsonObject.has("online")) {
                //статус онлайн
                val status = gson.fromJson(text, OnlineStatus::class.java)
                onlineStatus.postValue(status)
            }
        }
    }

    fun clear() {
        message.value=null
    }

    fun connect(uid: String) {
        webSocketRepository.connect(webSocketListener, uid)
    }

    fun disconnect() {
        webSocketRepository.disconnect()
    }

    fun sendMessage(message: String) {
        webSocketRepository.send(message)
    }

}