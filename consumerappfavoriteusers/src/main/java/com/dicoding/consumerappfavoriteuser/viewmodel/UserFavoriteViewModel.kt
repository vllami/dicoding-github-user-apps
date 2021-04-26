package com.dicoding.consumerappfavoriteuser.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.consumerappfavoriteuser.db.Contract
import com.dicoding.consumerappfavoriteuser.db.Helper
import com.dicoding.consumerappfavoriteuser.model.data.GithubUser

class UserFavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var favoriteUsersList = MutableLiveData<ArrayList<GithubUser>>()

    fun setFavoriteUsers(context: Context) {
        val cursor = context.contentResolver.query(
            Contract.Column.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val dataCursor = Helper.dataCursorList(cursor)
        favoriteUsersList.postValue(dataCursor)
    }

    fun getFavoriteUsers(): LiveData<ArrayList<GithubUser>> = favoriteUsersList

}