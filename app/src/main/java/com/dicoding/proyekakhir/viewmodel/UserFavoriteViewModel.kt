package com.dicoding.proyekakhir.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.proyekakhir.db.FavoriteUsersDB
import com.dicoding.proyekakhir.db.room.FavoriteUsers
import com.dicoding.proyekakhir.db.room.FavoriteUsersDAO

class UserFavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var favoriteUsersDAO: FavoriteUsersDAO?
    private var favoriteUsersDB: FavoriteUsersDB? = FavoriteUsersDB.getFavoriteUsersDB(application)

    init {
        favoriteUsersDAO = favoriteUsersDB?.favoriteUsersDAO()
    }

    fun getListFavoriteUsers(): LiveData<List<FavoriteUsers>>? {
        return favoriteUsersDAO?.getFavoriteUsers()
    }

}