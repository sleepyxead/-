package com.example.orders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.orders.Views.Fragments.ChatFragment
import com.example.orders.Views.Fragments.KvitFragment
import com.example.orders.Views.Fragments.MapFragment
import com.example.orders.Views.Fragments.OrdersFragment
import com.example.orders.Views.Fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(OrdersFragment())
        val bottomNavigationMenu: BottomNavigationView = findViewById(R.id.botNav)
        bottomNavigationMenu.selectedItemId = R.id.Order
        bottomNavigationMenu.setOnItemSelectedListener {
            when(it.itemId){
                R.id.Order -> replaceFragment(OrdersFragment())
                R.id.Map -> replaceFragment(MapFragment())
                R.id.Mess -> replaceFragment(ChatFragment())
                R.id.Kvit -> replaceFragment(KvitFragment())
                R.id.Profile ->replaceFragment(ProfileFragment())
                else ->{}
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment){
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.FrameLayout,fragment)
        ft.commit()
    }
}