package com.example.orders.Views.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.orders.Adapters.UserAdapter
import com.example.orders.Item.User
import com.example.orders.Listiners.IUserListener
import com.example.orders.Utilities.Repository
import com.example.orders.Views.Activities.ChatActivity
import com.example.orders.databinding.FragmentChatBinding


class ChatFragment : Fragment() {

    private lateinit var binding: FragmentChatBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = UserAdapter()
        binding.rec.adapter = adapter
        adapter.setData(Repository.getUsers())
        adapter.attachListener(object : IUserListener{
            override fun onUserClicked(user: User) {
                val intent = Intent(requireContext(), ChatActivity::class.java)
                intent.putExtra("user", user.name)
                startActivity(intent)
            }
        })
    }
}