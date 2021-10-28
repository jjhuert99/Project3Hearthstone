package com.example.project3hearthstone.favoritesdatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoritesDatabaseDao {
    @Insert
    fun insert(aFav: Favorite)

    @Query("SELECT * from favorite_cards_table WHERE cardId = :cardKey")
    fun get(cardKey: Long): Favorite

    @Query("DELETE FROM favorite_cards_table")
    fun clear()

    @Query("SELECT * FROM favorite_cards_table ORDER BY cardId DESC")
    fun getAllFav(): LiveData<List<Favorite>>

    @Query("SELECT * FROM favorite_cards_table ORDER BY cardId DESC LIMIT 1")
    fun getOneFav(): Favorite?
}