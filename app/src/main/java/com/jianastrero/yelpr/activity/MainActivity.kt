package com.jianastrero.yelpr.activity

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.jianastrero.yelpr.R
import com.jianastrero.yelpr.REQUEST_PERMISSION_LOCATION
import com.jianastrero.yelpr.dialog.askConfirmation
import com.jianastrero.yelpr.extension.log
import com.jianastrero.yelpr.viewmodel.MainViewModel
import com.jianastrero.yelpr.viewmodel.factory.YelprViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, YelprViewModelFactory.getInstance())
            .get(MainViewModel::class.java)

        if (locationPermissions.all { checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED }) {
            getLatestLocation()
        } else {
            requestPermission()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLatestLocation()
            } else {
                requestPermission()
            }
        }
    }

    private fun requestPermission() {
        CoroutineScope(Dispatchers.Default).launch {
            val confirmed = askConfirmation(supportFragmentManager) {
                setTitle("Location Permission")
                setMessage("Yelpr needs your permission to allow getting last location and location updates. Pressing cancel will close the app.")
                setPositiveText("Ok")
                setNegativeText("Cancel")
            }

            "Confirmed: $confirmed".log()

            if (confirmed) {
                withContext(Dispatchers.Main) {
                    requestPermissions(locationPermissions, REQUEST_PERMISSION_LOCATION)
                }
            } else {
                withContext(Dispatchers.Main) {
                    finish()
                }
            }
        }
    }

    private fun getLatestLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationClient.lastLocation
            .addOnSuccessListener {
                if (it != null) {
                    viewModel.latitude = it.latitude
                    viewModel.longitude = it.longitude
                }
            }
    }
}
