package com.example.project3hearthstone

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.example.project3hearthstone.classscreen.CardsByClassAdapter
import com.example.project3hearthstone.favoritesdatabase.Favorite
import com.example.project3hearthstone.favoritesscreen.FavoritesAdapter
import com.example.project3hearthstone.network.CardsByClass
import com.example.project3hearthstone.network.CardsBySearch
import com.example.project3hearthstone.searchresultsscreen.SearchResultsAdapter


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?){

    imgUrl?.let{
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(RequestOptions()
                .error(R.drawable.ic_baseline_image_not_supported_24))
            .into(imgView)
    }
}

@BindingAdapter("queryResults")
fun bindSearchResults(recyclerView: RecyclerView, results: List<CardsBySearch>?){
    val adapter = recyclerView.adapter as SearchResultsAdapter
    adapter.submitList(results)
}

@BindingAdapter("cardData")
fun bindRecyclerView(recylerView: RecyclerView, data: List<CardsByClass>?){
    val adapter = recylerView.adapter as CardsByClassAdapter
    adapter.submitList(data)
}

@BindingAdapter("favsData")
fun bindFavsDatabase(recyclerView: RecyclerView, data: List<Favorite>?){
    val adapter = recyclerView.adapter as FavoritesAdapter
    adapter.submitList(data)
}