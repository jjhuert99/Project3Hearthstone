package com.example.project3hearthstone.network

data class SingleCard(
        val cardId: String?=null,
        val name: String?=null,
        val playerClass: String?=null,
        val type: String?=null,
        val rarity: String?=null,
        val cardSet: String?=null,
        //Cards Description
        val text: String?=null
)