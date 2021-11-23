package com.example.project3hearthstone.di

import android.content.Context
import androidx.room.Room
import com.example.project3hearthstone.favoritesdatabase.FavoritesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FavComponent {

    @Singleton
    @Provides
    fun provideFavDB(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        FavoritesDatabase::class.java,
        "favorite_cards_table"
    ).build()

    @Singleton
    @Provides
    fun provideFavDao(db: FavoritesDatabase) = db.favoritesDatabaseDao
}
