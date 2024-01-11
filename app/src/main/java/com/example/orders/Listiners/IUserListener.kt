package com.example.orders.Listiners

import com.example.orders.Item.User

interface IUserListener {
    fun onUserClicked(user: User)
}