package com.example.project3hearthstone

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.project3hearthstone.classscreen.CardsByClassAdapter
import com.example.project3hearthstone.network.CardsByClass

@BindingAdapter("cardData")
fun bindRecyclerView(recylerView: RecyclerView, data: List<CardsByClass>?){
    val adapter = recylerView.adapter as CardsByClassAdapter
    adapter.submitList(data)
}