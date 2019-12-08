package com.example.sfs_prto.ui.current_order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sfs_prto.R

class CurrentOrderFragment : Fragment() {

    private lateinit var currentorderViewModel: CurrentOrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        currentorderViewModel =
            ViewModelProviders.of(this).get(CurrentOrderViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_current_order, container, false)
        val textView: TextView = root.findViewById(R.id.text_current_order)
        currentorderViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}