package com.example.orders.Views.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.orders.R
import com.example.orders.Views.Activities.OrderActivity
import com.example.orders.databinding.FragmentMapBinding
import com.example.orders.databinding.FragmentOrdersBinding


class OrdersFragment : Fragment() {

    private lateinit var binding: FragmentOrdersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val intent = Intent(requireContext(), OrderActivity::class.java)
        binding.apply {
            car.setOnClickListener {
                intent.putExtra("part","car")
                startActivity(intent)
            }
            bit.setOnClickListener {
                intent.putExtra("part","bit")
                startActivity(intent)
            }
            com.setOnClickListener {
                intent.putExtra("part","com")
                startActivity(intent)
            }
            hard.setOnClickListener {
                intent.putExtra("part","hard")
                startActivity(intent)
            }
        }
    }
}