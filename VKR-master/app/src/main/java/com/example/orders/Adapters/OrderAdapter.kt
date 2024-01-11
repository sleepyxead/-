package com.example.orders.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.orders.Item.Order
import com.example.orders.Item.User
import com.example.orders.Listiners.IOrderListener
import com.example.orders.Listiners.IUserListener
import com.example.orders.databinding.OrderViewholderBinding
import com.example.orders.databinding.UserViewholderBinding

class OrderAdapter :
    RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    private lateinit var list : MutableList<Order>
    private lateinit var listener: IOrderListener

    fun attachListener(listener: IOrderListener) {
        this.listener = listener
    }
    fun setData(newList : List<Order>){
        list = newList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = OrderViewholderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = list[position]
        holder.bind(news,listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(private val binding: OrderViewholderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(order: Order, listener: IOrderListener) {
            binding.apply {
                binding.root.setOnClickListener{
                    listener.onOrderClicked(order)
                }
                binding.Name.text = order.name
                binding.Address.text = order.address
                binding.problem.text = order.problem
                binding.time.text=order.time
            }
        }
    }
}