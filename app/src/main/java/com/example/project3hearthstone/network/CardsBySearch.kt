package com.example.project3hearthstone.network

data class CardsBySearch(
        val cardId: String,
        val name: String,
        val type: String,
        val rarity: String,
        val cardSet: String
)