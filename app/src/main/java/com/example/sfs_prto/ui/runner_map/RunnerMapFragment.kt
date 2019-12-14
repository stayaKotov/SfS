package com.example.sfs_prto.ui.runner_map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.sfs_prto.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_map.*



class RunnerMapFragment  : Fragment()  {


    private lateinit var runnerMapViewModel: RunnerMapViewModel
//    var mapView: MapView? = null
//    var map: GoogleMap? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        runnerMapViewModel =
            ViewModelProviders.of(this).get(RunnerMapViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_map, container, false)

        var mapView = root.findViewById<MapView>(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync{
            onMapReady(it)
        }

//        mapView.getMapAsync{
//            onMapReady(this)
//        }
//
        return root
    }
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    fun onMapReady(googleMap: GoogleMap) {
        var gmap = googleMap
        gmap.setMinZoomPreference((17).toFloat())
        val ny = LatLng(55.700413, 37.759619)
        gmap.moveCamera(CameraUpdateFactory.newLatLng(ny))
    }

}