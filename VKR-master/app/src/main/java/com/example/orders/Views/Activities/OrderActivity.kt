package com.example.orders.Views.Activities

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.orders.Adapters.OrderAdapter
import com.example.orders.Item.Order
import com.example.orders.Listiners.IOrderListener
import com.example.orders.R
import com.example.orders.Utilities.Repository
import com.example.orders.databinding.ActivityOrderBinding

class OrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    private var list = ArrayList<Order>()
    private val adapter = OrderAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val part = intent.extras!!.getString("part")

        binding.rec.adapter = adapter
        adapter.attachListener(object : IOrderListener{
            override fun onOrderClicked(order: Order) {
                showcustomdialog(order)
            }

        })

        binding.search.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val res =  list.filter { order -> order.name.trim().contains(s.toString().trim()) || order.problem.trim().contains(s.toString().trim()) || order.address.trim().contains(s.toString().trim()) }
                adapter.setData(res)
            }

        })
        when (part) {
            "car" -> {
                list = Repository.getCarsOrders()
                adapter.setData(list)
            }
            "bit" -> {
                list = Repository.getBitOrders()
                adapter.setData(list)
            }
            "com" -> {
                list = Repository.getComOrders()
                adapter.setData(list)
            }
            else -> {
                list = Repository.getHardOrders()
                adapter.setData(list)
            }
        }
    }
    private fun showcustomdialog(order: Order) {
        val dialog = Dialog(this@OrderActivity)
        dialog.setContentView(R.layout.check_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val title = dialog.findViewById<TextView>(R.id.title)
        val textTime = dialog.findViewById<TextView>(R.id.textTime)
        val desc = dialog.findViewById<TextView>(R.id.desc)
        val acc = dialog.findViewById<Button>(R.id.accept)
        val end = dialog.findViewById<Button>(R.id.end)

        title.text = order.problem
        textTime.text = order.time
        desc.text = order.description
        acc.setOnClickListener {
            list.remove(order)
            adapter.setData(list)
            dialog.dismiss()
        }
        end.setOnClickListener {
            list.remove(order)
            adapter.setData(list)
            dialog.dismiss()
        }
        val slideInTopAnimation = AnimationUtils.loadAnimation(this@OrderActivity, R.anim.slide_in_top)
        dialog.window?.decorView?.startAnimation(slideInTopAnimation)
        dialog.show()
    }
}