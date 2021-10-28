package com.example.project3hearthstone.favoritesdatabase
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_cards_table")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    var cardId: Long = 0L,

    @ColumnInfo(name = "card_name")
    var cardName: String = "",

    @ColumnInfo(name = "card_type")
    var cardType: String = "",

    @ColumnInfo(name = "card_rarity")
    var cardRarity: String = "",

    @ColumnInfo(name = "card_set")
    var cardSet: String = ""
)