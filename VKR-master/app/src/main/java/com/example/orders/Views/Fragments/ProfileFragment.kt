package com.example.orders.Views.Fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.orders.MainActivity
import com.example.orders.R
import com.example.orders.Utilities.DatabaseHelper
import com.example.orders.Utilities.PreferenceManager
import com.example.orders.Views.Activities.LoginActivity
import com.example.orders.databinding.FragmentOrdersBinding
import com.example.orders.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manager = PreferenceManager(requireContext())
        binding.emailtext.text = manager.getString("Email")
        binding.passtext.setText(manager.getString("Password"))
        binding.nametext.setText(manager.getString("Name"))

        binding.button.setOnClickListener {
            if (binding.nametext.text.isNotEmpty() && binding.passtext.text.length>2){
                manager.putString("Password",binding.passtext.text.toString())
                manager.putString("Name",binding.nametext.text.toString())
                showToast("Поля сохранены")
            }else{
                showToast("Данные не корректны")
            }
        }
        binding.logout.setOnClickListener {
            manager.putBoolean("skip",false)
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}