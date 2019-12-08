package com.example.sfs_prto.ui.runner_map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sfs_prto.R

class RunnerMapFragment : Fragment() {

    private lateinit var runnerMapViewModel: RunnerMapViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        runnerMapViewModel =
            ViewModelProviders.of(this).get(RunnerMapViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_runner_map, container, false)
        return root
    }
}