package com.mini.spotify.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mini.spotify.datamodel.Album

@Database(entities = [Album::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao
}