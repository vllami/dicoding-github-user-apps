package com.dicoding.proyekakhir.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.proyekakhir.model.data.GithubUser
import com.dicoding.proyekakhir.model.data.GithubUserArray
import com.dicoding.proyekakhir.retrofit.GithubClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val search = MutableLiveData<ArrayList<GithubUser>>()

    fun setSearch(searchQuery: String) {
        GithubClient.githubService.getSearchEndpoint(searchQuery).enqueue(object : Callback<GithubUserArray> {
            override fun onResponse(call: Call<GithubUserArray>, response: Response<GithubUserArray>) {
                if (response.isSuccessful) search.postValue(response.body()?.items)
            }

            override fun onFailure(call: Call<GithubUserArray>, throwable: Throwable) {
                Log.d("Failure ", throwable.message.toString())
            }
        })
    }

    fun getSearch(): LiveData<ArrayList<GithubUser>> = search

}