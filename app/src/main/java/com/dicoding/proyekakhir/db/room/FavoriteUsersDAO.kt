package com.dicoding.proyekakhir.db.room

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUsersDAO {

    @Insert
    suspend fun addFavoriteUsers(favoriteUsers: FavoriteUsers)

    @Query("SELECT count(*) FROM favorite_users WHERE favorite_users.id = :id")
    suspend fun checkFavoriteUsers(id: Int) : Int

    @Query("DELETE FROM favorite_users WHERE favorite_users.id = :id")
    suspend fun removeFavoriteUsers(id: Int) : Int

    @Query("SELECT * FROM favorite_users")
    fun getFavoriteUsers(): LiveData<List<FavoriteUsers>>

    @Query("SELECT * FROM favorite_users")
    fun consumerFavoriteUsers(): Cursor

}