package com.apex.codeassesment.ui.location

import android.Manifest
import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.apex.codeassesment.R
import com.apex.codeassesment.databinding.ActivityLocationBinding
import dagger.hilt.android.AndroidEntryPoint


// TODO (Optional Bonus 8 points): Calculate distance between 2 coordinates using phone's location
@AndroidEntryPoint
class LocationActivity : AppCompatActivity() {

  lateinit var locationManager: LocationManager

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val binding = ActivityLocationBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val latitudeRandomUser = intent.getStringExtra("user-latitude-key")
    val longitudeRandomUser = intent.getStringExtra("user-longitude-key")
    locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

    binding.locationRandomUser.text = getString(R.string.location_random_user, latitudeRandomUser, longitudeRandomUser)
    binding.locationCalculateButton.setOnClickListener {

      checkPermissions()
      Toast.makeText(this, "TODO (8): Bonus - Calculate distance between 2 coordinates using phone's location", Toast.LENGTH_SHORT).show()
    }


  }

  fun checkPermissions() {
    if (ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
      ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
      ) != PackageManager.PERMISSION_GRANTED
    ) {



      ActivityCompat.requestPermissions(
        (this as Activity?)!!,
        arrayOf<String>(
          Manifest.permission.ACCESS_FINE_LOCATION,
          Manifest.permission.ACCESS_COARSE_LOCATION
        ),
        101
      )
    } else {
      // getting GPS status
      val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

      // getting network status
      val isNetworkEnabled = locationManager
        .isProviderEnabled(LocationManager.NETWORK_PROVIDER)


      if (isGPSEnabled) {
        gpsProvider()
      } else if (isNetworkEnabled) {

      }

    }
  }


  fun gpsProvider() {
//    if (isGPSEnabled) {
//      if (location == null) {
        //check the network permission
        if (ActivityCompat.checkSelfPermission(
            baseContext,
            Manifest.permission.ACCESS_FINE_LOCATION
          ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            baseContext,
            Manifest.permission.ACCESS_COARSE_LOCATION
          ) != PackageManager.PERMISSION_GRANTED
        ) {
          ActivityCompat.requestPermissions(
            (baseContext as Activity?)!!,
            arrayOf<String>(
              Manifest.permission.ACCESS_FINE_LOCATION,
              Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            101
          )
        }
//        locationManager.requestLocationUpdates(
//          LocationManager.GPS_PROVIDER,
//          500L,
//          100f, this
//        )
        Log.d("GPS Enabled", "GPS Enabled")
//        if (locationManager != null) {
//          location = locationManager
//            .getLastKnownLocation(LocationManager.GPS_PROVIDER)
//          if (location != null) {
//            latitude = location.getLatitude()
//            longitude = location.getLongitude()
//          }
//        }
//      }
//    }
  }


  interface LocationListener {
    fun onLocationChanged(var1: Location?)
    fun onStatusChanged(var1: String?, var2: Int, var3: Bundle?)
    fun onProviderEnabled(var1: String?)
    fun onProviderDisabled(var1: String?)
  }

//  internal class GpsTracker : Service(), LocationListener {
//    override fun onLocationChanged(location: Location) {}
//    override fun onProviderDisabled(provider: String) {}
//    override fun onProviderEnabled(provider: String) {}
//    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
//    override fun onBind(p0: Intent?): IBinder? {
//      TODO("Not yet implemented")
//    }
//  }
  /* Class My Location Listener */
//  class MyLocationListener(val context: Context) : LocationListener {
//    override fun onLocationChanged(loc: Location) {
//      loc.getLatitude()
//      loc.getLongitude()
//      val Text = "My current location is: " +
//              "Latitude = " + loc.getLatitude() +
//              "Longitude = " + loc.getLongitude()
//      Toast.makeText(context, Text, Toast.LENGTH_SHORT).show()
//    }
//
//    override fun onProviderDisabled(provider: String) {
//      Toast.makeText(context, "Gps Disabled", Toast.LENGTH_SHORT).show()
//    }
//
//    override fun onProviderEnabled(provider: String) {
//      Toast.makeText(context, "Gps Enabled", Toast.LENGTH_SHORT).show()
//    }
//
//    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {
//
//    }
//  }

}
