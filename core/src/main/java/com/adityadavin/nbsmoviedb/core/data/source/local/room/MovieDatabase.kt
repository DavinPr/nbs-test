package com.adityadavin.nbsmoviedb.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.adityadavin.nbsmoviedb.core.data.source.local.entity.*

@Database(
    entities = [
        MovieBannerEntity::class,
        MoviePopularEntity::class,
        MovieComingSoonEntity::class,
        MoviePopularOnYearEntity::class,
        MovieFavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}