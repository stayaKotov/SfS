package com.example.sfs_prto.ui.current_order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrentOrderViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Current Order Fragment"
    }
    val text: LiveData<String> = _text
}