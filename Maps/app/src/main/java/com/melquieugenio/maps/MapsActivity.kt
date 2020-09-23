package com.melquieugenio.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mCurrentLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        if (Build.VERSION.SDK_INT < 23) {
            callMapAsync()
        } else if (isLocationPermissionGranted()) {
            callMapAsync()
        }
    }

    private fun callMapAsync() {
        val mapFragment = map as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    // Called when map is ready, deal with it here.
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    mCurrentLocation = location
                    val myLtgLng = LatLng(mCurrentLocation.latitude, mCurrentLocation.longitude)
                    val zoomLevel = 15F
                    googleMap.addMarker(
                        MarkerOptions().position(myLtgLng).title("I'm here.")
                    )
                    googleMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            myLtgLng,
                            zoomLevel
                        )
                    )
                } else {
                    Toast.makeText(
                        this,
                        "Please turn on your GPS and restart the app.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isLocationPermissionGranted(): Boolean {

        // Handling request permission responses and allocating a reference to it
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    callMapAsync()
                } else {
                    Toast.makeText(
                        this,
                        "Location permission needed to update map.",
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
                return true
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
            -> { // true when user try to access func after denying permission
                Toast.makeText(
                    this,
                    "Please let the app to access the location for updating the map.",
                    Toast.LENGTH_LONG
                ).show()

                return false
            }
            else -> { // When not granted, requesting for the first time
                requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }
        }

        return false
    }
}