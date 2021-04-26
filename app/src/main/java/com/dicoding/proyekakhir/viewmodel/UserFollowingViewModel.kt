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

class UserFollowingViewModel : ViewModel() {

    val userFollowing = MutableLiveData<ArrayList<GithubUser>>()

    fun setUserFollowing(username: String) {
        GithubClient.githubService.getUserFollowingEndpoint(username).enqueue(object : Callback<ArrayList<GithubUser>> {
            override fun onResponse(call: Call<ArrayList<GithubUser>>, response: Response<ArrayList<GithubUser>>) {
                if (response.isSuccessful) userFollowing.postValue(response.body())
            }

            override fun onFailure(call: Call<ArrayList<GithubUser>>, throwable: Throwable) {
                Log.d("onFailure ", throwable.message.toString())
            }
        })
    }

    fun getUserFollowing(): LiveData<ArrayList<GithubUser>> = userFollowing

}