package com.example.project3hearthstone.mapscreen

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.project3hearthstone.BottomSheet
import com.example.project3hearthstone.Common
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
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    //private lateinit var passVicinity: String
    var inMins: String = ""
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
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
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
        geoLocate()

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

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_CODE
            )

            return
        }
        mMap.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->

            if (location != null) {
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                placeMarkerOnMap(currentLatLong)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 12f))
                nearbyPlaces(location.latitude, location.longitude)
            }
        }
    }

    private fun moveCamera(latLng: LatLng, zoom: Float) {
        Log.d(
            "JJJ",
            "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
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
        //googlePlaceUrl.append("&type=restaurant")
        googlePlaceUrl.append("&key=AIzaSyB9X7Hc3AAMziJcznFxon9GyoAspANiSVQ")

        return googlePlaceUrl.toString()
    }

    fun geoLocate() {
        val geocoder = Geocoder(this)
        var list: List<Address> = ArrayList()
        try {
            list = geocoder.getFromLocationName("Pharr", 1)
        } catch (e: IOException) {
            Log.e("JJJ", "geoLocate: IOException: ")
        }
        if (list.size > 0) {
            val address = list[0]
            val currentLatLong = LatLng(address.latitude, address.longitude)
            placeMarkerOnMap(currentLatLong)
        }
    }


    private fun placeMarkerOnMap(currentLatLong: LatLng) {
        val markerOptions = MarkerOptions().position(currentLatLong)
        markerOptions.title("$currentLatLong")
        markerOptions.title("$currentLatLong")
        mMap.addMarker(markerOptions)
    }

    override fun onMarkerClick(p0: Marker) = false
}