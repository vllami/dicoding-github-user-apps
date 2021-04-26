package com.dicoding.proyekakhir.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.proyekakhir.db.room.FavoriteUsers
import com.dicoding.proyekakhir.db.room.FavoriteUsersDAO

@Database(
    entities = [FavoriteUsers::class],
    version = 1
)

abstract class FavoriteUsersDB : RoomDatabase() {

    companion object {
        @Volatile
        private var FAVORITE_USERS_INSTANCE: FavoriteUsersDB? = null

        fun getFavoriteUsersDB(context: Context): FavoriteUsersDB? {
            if (FAVORITE_USERS_INSTANCE == null) {
                synchronized(FavoriteUsersDB::class) {
                    if (FAVORITE_USERS_INSTANCE == null) FAVORITE_USERS_INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteUsersDB::class.java,
                        "favorite_users_db"
                    ).build()
                }
            }
            return FAVORITE_USERS_INSTANCE
        }
    }

    abstract fun favoriteUsersDAO(): FavoriteUsersDAO

}