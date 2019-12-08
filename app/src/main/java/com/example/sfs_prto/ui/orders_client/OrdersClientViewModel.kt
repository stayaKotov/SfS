package com.example.sfs_prto.ui.orders_client

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrdersClientViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Orders client Fragment"
    }
    val text: LiveData<String> = _text
}