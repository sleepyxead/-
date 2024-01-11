package com.example.orders.Views.Activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.orders.Adapters.ChatAdapter
import com.example.orders.Item.ChatMessage
import com.example.orders.Utilities.Repository
import com.example.orders.databinding.ActivityChatBinding
import java.text.SimpleDateFormat
import java.util.Date

class ChatActivity : AppCompatActivity() {
    private lateinit var binding : ActivityChatBinding
    private var list = ArrayList<ChatMessage>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = ChatAdapter()
        binding.recyclerView2.adapter= adapter
        val name = intent.extras!!.getString("user")
        binding.textView2.text = name
        binding.recyclerView2.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
        when (name) {
            "Аркадий" -> {
                list = Repository.getMessArk()
                adapter.setData(list)
            }
            "Никита" -> {
                list = Repository.getMessNik()
                adapter.setData(list)
            }
            else -> {
                list = Repository.getMessVadim()
                adapter.setData(list)
            }
        }
        binding.imageButton3.setOnClickListener {
            onBackPressed()
        }
        binding.sendmess.setOnClickListener {
            if (binding.InputMMessage.text.isNotEmpty()){
                val sdf = SimpleDateFormat("hh:mm")
                val currentDate = sdf.format(Date())
                list.add(ChatMessage(binding.InputMMessage.text.toString(),currentDate!!,0))
                binding.recyclerView2.smoothScrollToPosition(list.size - 1)
                adapter.setData(list)
                binding.InputMMessage.setText("")
            }
        }
    }
}