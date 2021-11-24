package com.example.project3hearthstone.common

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.project3hearthstone.R

@BindingAdapter("CharacterImg")
fun bindImage(imgView: ImageView, imgUrl: String?){
    if (imgUrl != null){
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_baseline_image_not_supported_24)
                    .error(R.drawable.ic_baseline_image_not_supported_24))
            .into(imgView)
    }
}
