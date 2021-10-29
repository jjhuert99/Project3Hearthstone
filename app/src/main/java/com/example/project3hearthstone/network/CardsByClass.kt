package com.example.project3hearthstone.network

data class CardsByClass(
        val cardId: String,
        val name: String,
        val type: String,
        val rarity: String?=null,
        val cardSet: String,
        val img: String?=null
)