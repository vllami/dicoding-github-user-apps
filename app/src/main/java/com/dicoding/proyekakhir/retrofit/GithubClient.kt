package com.dicoding.proyekakhir.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GithubClient {

    private val retrofit = Retrofit.Builder().baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val githubService: GithubService = retrofit.create(GithubService::class.java)

}