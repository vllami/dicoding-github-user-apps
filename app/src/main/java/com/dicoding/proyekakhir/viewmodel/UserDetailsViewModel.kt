package com.dicoding.proyekakhir.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.proyekakhir.db.FavoriteUsersDB
import com.dicoding.proyekakhir.db.room.FavoriteUsers
import com.dicoding.proyekakhir.db.room.FavoriteUsersDAO
import com.dicoding.proyekakhir.model.data.GithubUserDetails
import com.dicoding.proyekakhir.retrofit.GithubClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private var favoriteUsersDAO: FavoriteUsersDAO?
    private var favoriteUsersDB: FavoriteUsersDB? = FavoriteUsersDB.getFavoriteUsersDB(application)

    init {
        favoriteUsersDAO = favoriteUsersDB?.favoriteUsersDAO()
    }

    private val userDetails = MutableLiveData<GithubUserDetails>()

    fun setUserDetails(username: String) {
        GithubClient.githubService.getUserDetailsEndpoint(username).enqueue(object : Callback<GithubUserDetails> {
            override fun onResponse(call: Call<GithubUserDetails>, response: Response<GithubUserDetails>) {
                if (response.isSuccessful) userDetails.postValue(response.body())
            }

            override fun onFailure(call: Call<GithubUserDetails>, throwable: Throwable) {
                Log.d("Failure ", throwable.message.toString())
            }
        })
    }

    fun getUserDetails(): LiveData<GithubUserDetails> = userDetails

    suspend fun checkFavoriteUsers(id: Int) = favoriteUsersDAO?.checkFavoriteUsers(id)

    fun addFavoriteUsers(username: String, id: Int, avatar: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val githubFavoriteUsers = FavoriteUsers(username, id, avatar)
            favoriteUsersDAO?.addFavoriteUsers(githubFavoriteUsers)
            Log.d("Failure ", githubFavoriteUsers.toString())
        }
    }

    fun removeFavoriteUsers(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteUsersDAO?.removeFavoriteUsers(id)
            Log.d("Failure ", id.toString())
        }
    }

}