package com.example.project3hearthstone.mapscreen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.project3hearthstone.BottomSheet
import com.example.project3hearthstone.Common
import com.example.project3hearthstone.MainActivity
import com.example.project3hearthstone.R
import com.example.project3hearthstone.network.MapPlacesApiService
import com.example.project3hearthstone.network.MyPlaces
import com.example.project3hearthstone.network.Results
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var arrayPlaces: Array<Results?> = arrayOfNulls(30)

    companion object {
        private const val LOCATION_REQUEST_CODE = 1
    }

    lateinit var mService: MapPlacesApiService
    internal lateinit var currentPlace: MyPlaces

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mService = Common.googleApiService
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navbar)
        bottomNavigationView.setOnNavigationItemSelectedListener{item ->
            val intent = Intent(this, MainActivity::class.java)
            when(item.itemId){
                R.id.homeScreenFragment ->{
                    startActivity(intent)
                    true
                }
                R.id.favoritesFragment->{
                    startActivity(intent)
                    true
                }
                R.id.mapsActivity->{
                    true
                }

                else -> {true
                }
            }
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isMyLocationButtonEnabled = true
        mMap.setOnMarkerClickListener{ marker ->
            val passVicinity = getVicinity(marker.title!!)
            val passLat = getLat(marker.title!!)
            val passLng = getLng(marker.title!!)
            val inMins = calcDist(passLat, passLng)
            val bottomSheet1 = BottomSheet(marker.title!!, passVicinity, passLat, passLng, inMins)
            bottomSheet1.show(supportFragmentManager, "bottom sheet 1")
            true
        }
        setUpMap()

    }
    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
            return
        }

        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->

            if (location != null) {
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLong)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 11f))
                nearbyPlaces(location.latitude, location.longitude)
            }
        }
    }

    private fun calcDist(passLat: String, passLng: String): String {
        val newLoc = Location("locationA")
        newLoc.latitude = passLat.toDouble()
        newLoc.longitude = passLng.toDouble()

        val ourLoc = Location("locationA")
        ourLoc.latitude = lastLocation.latitude
        ourLoc.longitude = lastLocation.longitude

        val distBetween: Float = ourLoc.distanceTo(newLoc)
        return (distBetween.toInt()/1109).toString()
    }

    private fun getVicinity(x: String): String {
        for(i in 0 until arrayPlaces.size){
            if(x == arrayPlaces[i]!!.name)
            {
                return arrayPlaces[i]!!.vicinity!!
            }
        }
        return "Noodle Road"
    }
    private fun getLng(x: String): String {
        for(i in 0 until arrayPlaces.size){
            if(x == arrayPlaces[i]!!.name)
            {
                return arrayPlaces[i]!!.geometry!!.location!!.lng!!.toString()

            }
        }
        return "Noodle Road"
    }
    private fun getLat(x: String): String {
        for(i in 0 until arrayPlaces.size){
            if(x == arrayPlaces[i]!!.name)
            {
                return arrayPlaces[i]!!.geometry!!.location!!.lat!!.toString()
            }
        }
        return "Noodle Road"
    }

    private fun nearbyPlaces(latitude: Double, longitude: Double) {
        mMap.clear()
        val url = getUrl(latitude, longitude)
        mService.getPlaces(url)
            .enqueue(object : Callback<MyPlaces> {
                override fun onResponse(call: Call<MyPlaces>?, response: Response<MyPlaces>?) {
                    currentPlace = response!!.body()!!
                    if (response!!.isSuccessful) {
                        for (i in 0 until response!!.body()!!.results!!.size) {

                            val googlePlace = response.body()!!.results!![i]
                            arrayPlaces[i] = response.body()!!.results!![i]

                            val lat = googlePlace.geometry!!.location!!.lat
                            val lng = googlePlace.geometry!!.location!!.lng

                            val placeName = googlePlace.name
                            val latLng = LatLng(lat, lng)
                            val markerOptions = MarkerOptions()
                            markerOptions.position(latLng)
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.logo_copy))
                            markerOptions.title("$placeName")

                            mMap.addMarker(markerOptions)
                        }
                    }
                }
                override fun onFailure(call: Call<MyPlaces>?, t: Throwable) {
                    Toast.makeText(baseContext, "" + t.message, Toast.LENGTH_LONG).show()
                }
            })
    }

    private fun getUrl(latitude: Double, longitude: Double): String {
        val googlePlaceUrl =
            StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
        googlePlaceUrl.append("?keyword=cards")
        googlePlaceUrl.append("&location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=20000")
        googlePlaceUrl.append("&key=AIzaSyB9X7Hc3AAMziJcznFxon9GyoAspANiSVQ")

        return googlePlaceUrl.toString()
    }

    private fun placeMarkerOnMap(currentLatLong: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLong)
        markerOptions.title("$currentLatLong")
        mMap.addMarker(markerOptions)
    }

    override fun onMarkerClick(p0: Marker) = false
}
