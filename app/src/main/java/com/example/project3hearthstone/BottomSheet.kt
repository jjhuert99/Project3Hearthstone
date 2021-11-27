package com.example.project3hearthstone

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet(
    val placeName: String,
    val placeVicinity: String,
    val positionLat: String,
    val positionLng: String,
    val inMins: String
): BottomSheetDialogFragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v: View = inflater.inflate(R.layout.bottomsheet, container, false)
        val text1: TextView = v.findViewById(R.id.ttop)
        text1.text = "${placeName}"

        val textM: TextView = v.findViewById(R.id.mileAway)
        textM.text = "${inMins} miles"

        val text2: TextView = v.findViewById(R.id.bbottom)
        text2.text = "${placeVicinity}"

        val openNav: ImageView = v.findViewById(R.id.buttonNav)
        openNav.setOnClickListener{
            val gmmIntentUri =
                Uri.parse("geo:${positionLat},${positionLng}?q=${placeName}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
        return v
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomClearTheme)
    }

}
