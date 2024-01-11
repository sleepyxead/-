package com.example.orders.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.orders.Item.User
import com.example.orders.Listiners.IUserListener
import com.example.orders.databinding.UserViewholderBinding

class UserAdapter :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private lateinit var list : MutableList<User>
    private lateinit var listener: IUserListener

    fun attachListener(listener: IUserListener) {
        this.listener = listener
    }
    fun setData(newList : List<User>){
        list = newList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserViewholderBinding.inflate(
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

    inner class ViewHolder(private val binding: UserViewholderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User,listener: IUserListener) {
            binding.apply {
                binding.root.setOnClickListener{
                    listener.onUserClicked(user)
                }
                binding.textname.text = user.name + " - " + user.problem
                binding.textrecent.text = user.lastMessage
                binding.textTime.text = user.time
            }
        }
    }
}