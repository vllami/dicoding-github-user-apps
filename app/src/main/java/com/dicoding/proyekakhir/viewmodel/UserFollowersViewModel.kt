package com.dicoding.proyekakhir.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.proyekakhir.model.data.GithubUser
import com.dicoding.proyekakhir.retrofit.GithubClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserFollowersViewModel : ViewModel() {

    val userFollowers = MutableLiveData<ArrayList<GithubUser>>()

    fun setUserFollowers(username: String) {
        GithubClient.githubService.getUserFollowersEndpoint(username).enqueue(object : Callback<ArrayList<GithubUser>> {
            override fun onResponse(call: Call<ArrayList<GithubUser>>, response: Response<ArrayList<GithubUser>>) {
                if (response.isSuccessful) userFollowers.postValue(response.body())
            }

            override fun onFailure(call: Call<ArrayList<GithubUser>>, throwable: Throwable) {
                Log.d("onFailure ", throwable.message.toString())
            }
        })
    }

    fun getUserFollowers(): LiveData<ArrayList<GithubUser>> = userFollowers

}