package com.example.orders.Views.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.orders.R
import com.example.orders.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment() {

    private lateinit var binding: FragmentMapBinding

    private val options = MarkerOptions()
    private val latlngs = ArrayList<LatLng>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val latitude = 55.773549// Ваша широта
        val longitude = 37.620551 // Ваша долгота
        val latLng = LatLng(latitude, longitude)

        val supportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync { googleMap ->
            val markerOptions = MarkerOptions()
                .position(latLng)
                .title("Моя метка")
            googleMap.addMarker(markerOptions)
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 11.4f)
            googleMap.moveCamera(cameraUpdate)
            latlngs.add(LatLng(55.82158, 37.63063))
            latlngs.add(LatLng(55.76729, 37.66454))
            latlngs.add(LatLng(55.757965, 37.569123))
            latlngs.add(LatLng(55.732101, 37.617286))
            latlngs.add(LatLng(55.743261, 37.639317))
            for (point in latlngs) {
                options.position(point!!)
                options.title("someTitle")
                options.snippet("someDesc")
                googleMap.addMarker(options)
            }
        }

    }
}