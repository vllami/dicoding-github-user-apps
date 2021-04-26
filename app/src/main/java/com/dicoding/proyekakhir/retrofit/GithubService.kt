package com.dicoding.proyekakhir.retrofit

import com.dicoding.proyekakhir.BuildConfig.GITHUB_TOKEN
import com.dicoding.proyekakhir.model.data.GithubUser
import com.dicoding.proyekakhir.model.data.GithubUserArray
import com.dicoding.proyekakhir.model.data.GithubUserDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("search/users")
    @Headers("Authorization: token $GITHUB_TOKEN")
    fun getSearchEndpoint(@Query("q") searchQuery: String): Call<GithubUserArray>

    @GET("users/{username}")
    @Headers("Authorization: token $GITHUB_TOKEN")
    fun getUserDetailsEndpoint(@Path("username") userDetails: String): Call<GithubUserDetails>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $GITHUB_TOKEN")
    fun getUserFollowersEndpoint(@Path("username") userFollowers: String): Call<ArrayList<GithubUser>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $GITHUB_TOKEN")
    fun getUserFollowingEndpoint(@Path("username") userFollowing: String): Call<ArrayList<GithubUser>>

}