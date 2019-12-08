package com.example.sfs_prto.ui.orders_client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sfs_prto.R

class OrdersClientFragment : Fragment() {

    private lateinit var ordersClientViewModel: OrdersClientViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ordersClientViewModel =
            ViewModelProviders.of(this).get(OrdersClientViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_orders_client, container, false)
        return root
    }
}