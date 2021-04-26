package com.dicoding.proyekakhir.model.data

data class GithubUserDetails(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val name: String? = null,
    val company: String? = null,
    val location: String? = null,
    val public_repos: Int
)