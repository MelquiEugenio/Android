package com.melquieugenio.maps

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        if (Build.VERSION.SDK_INT < 23)
            callMapAsync()
        else
            dealingWithRunTimeLocationPermission()
    }

    private fun callMapAsync() {
        val mapFragment = map as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    // Called when map is ready, deal with it here.
    override fun onMapReady(googleMap: GoogleMap) {

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun dealingWithRunTimeLocationPermission() {

        // Handling request permission responses and allocating a reference to it
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    callMapAsync() // its never called IDk why
                } else {
                    Toast.makeText(
                        this,
                        "The app need the location permission to show the map.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        // Troubleshooting permission
        when {
            ContextCompat.checkSelfPermission( // When granted
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                callMapAsync()
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) // If the user needs info
            -> {
                Toast.makeText(
                    this,
                    "Please let the app to access the location for updating the map.",
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> { // When not granted
                // Request permission
                requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        }
    }
}