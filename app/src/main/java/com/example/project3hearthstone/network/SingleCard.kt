package com.example.project3hearthstone.network

data class SingleCard(
        val cardId: String,
        val name: String,
        val playerClass: String,
        val type: String,
        val rarity: String,
        val cardSet: String,
        //Cards Description
        val text: String
)