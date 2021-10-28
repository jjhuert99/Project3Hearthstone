package com.example.project3hearthstone

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.project3hearthstone.classscreen.CardsByClassAdapter
import com.example.project3hearthstone.favoritesdatabase.Favorite
import com.example.project3hearthstone.favoritesscreen.FavoritesAdapter
import com.example.project3hearthstone.network.CardsByClass
import com.example.project3hearthstone.network.CardsBySearch
import com.example.project3hearthstone.searchresultsscreen.SearchResultsAdapter

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