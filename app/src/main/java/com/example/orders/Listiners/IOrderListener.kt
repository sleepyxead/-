package com.example.orders.Listiners

import com.example.orders.Item.Order

interface IOrderListener {
    fun onOrderClicked(order: Order)
}