package com.example.messenger.adapters

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.messenger.databinding.ItemMessageLeftBinding
import com.example.messenger.databinding.ItemMessageRightBinding
import com.example.messenger.methods.CustomDateUtils

import com.example.messenger.models.Message

class MessageAdapter(private val context: Context, private val messages: ArrayList<Message>, private val myUid: String) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {


    open class MessageViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        open fun bind(message:Message) {}
    }

    class MyMessageViewHolder(binding: ItemMessageRightBinding) : MessageViewHolder(binding.root) {
        private var messageText: TextView = binding.userMessageText
        private var timeText: TextView = binding.messageTime

        @RequiresApi(Build.VERSION_CODES.O)
        override fun bind(message: Message) {
            messageText.text = message.message
            if(message.time!=null)
            {
                timeText.text = message.formattedTime
            }
        }
    }
    class OtherMessageViewHolder(binding: ItemMessageLeftBinding) : MessageViewHolder(binding.root) {
        private var messageText: TextView = binding.userMessageText
        private var timeText: TextView = binding.messageTime

        @RequiresApi(Build.VERSION_CODES.O)
        override fun bind(message: Message) {
            messageText.text = message.message
            if(message.time!=null)
            {
                timeText.text = message.formattedTime
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return if(messages[position].senderUid == myUid) {
            VIEW_TYPE_MY_MESSAGE
        }
        else {
            VIEW_TYPE_OTHER_MESSAGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        if (viewType == VIEW_TYPE_MY_MESSAGE)
        {
            return MyMessageViewHolder(ItemMessageRightBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
        else
        {
            return OtherMessageViewHolder(ItemMessageLeftBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int {
        return messages.size
    }


    companion object {
        const val VIEW_TYPE_MY_MESSAGE = 0
        const val VIEW_TYPE_OTHER_MESSAGE = 1
    }
}