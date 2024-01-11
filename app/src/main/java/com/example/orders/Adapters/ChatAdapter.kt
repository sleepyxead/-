package com.example.orders.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.orders.Item.ChatMessage
import com.example.orders.databinding.RecievedMessageViewholderBinding
import com.example.orders.databinding.SentMessageViewholderBinding

class ChatAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list: List<ChatMessage>? = null
    private val VIEW_TYPE_SENT = 1
    private val VIEW_TYPE_RECEIVED = 2

    fun setData(newList : List<ChatMessage>){
        list = newList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_SENT) {
            SentMessageViewHolder(
                SentMessageViewholderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            ReceivedMessageViewHolder(
                RecievedMessageViewholderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_SENT) {
            (holder as SentMessageViewHolder).setData(list!![position])
        } else {
            (holder as ReceivedMessageViewHolder).setData(
                list!![position],
            )
        }
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (list!![position].type==0) {
            VIEW_TYPE_SENT
        } else {
            VIEW_TYPE_RECEIVED
        }
    }

    class SentMessageViewHolder(sentMessageViewholderBinding: SentMessageViewholderBinding) :
        RecyclerView.ViewHolder(sentMessageViewholderBinding.root) {
        private val binding: SentMessageViewholderBinding

        init {
            binding = sentMessageViewholderBinding
        }

        fun setData(chatMessage: ChatMessage) {
            binding.message.text = chatMessage.message
            binding.datetime.text = chatMessage.time
        }
    }

    class ReceivedMessageViewHolder(recievedMessageViewholderBinding: RecievedMessageViewholderBinding) :
        RecyclerView.ViewHolder(recievedMessageViewholderBinding.root) {
        private val binding: RecievedMessageViewholderBinding

        init {
            binding = recievedMessageViewholderBinding
        }

        fun setData(chatMessage: ChatMessage) {
            binding.message.text = chatMessage.message
            binding.datetime.text = chatMessage.time
        }
    }
}