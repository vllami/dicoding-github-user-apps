package com.dicoding.proyekakhir.db.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "favorite_users"
)

data class FavoriteUsers(
    val login: String,
    @PrimaryKey
    val id: Int,
    val avatar_url: String
) : Serializable