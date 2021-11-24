package com.example.project3hearthstone.network

data class SingleCard(
    val cardId: String?=null,
    val name: String?=null,
    val playerClass: String?=null,
    var type: String?=null,
    var rarity: String?=null,
    var cardSet: String?=null,
        //Cards Description
    val text: String?=null,
    val img: String?=null
)
