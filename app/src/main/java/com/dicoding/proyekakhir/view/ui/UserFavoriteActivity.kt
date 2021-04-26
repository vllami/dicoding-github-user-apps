package com.dicoding.proyekakhir.view.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.proyekakhir.R
import com.dicoding.proyekakhir.databinding.ActivityUserFavoriteBinding
import com.dicoding.proyekakhir.db.room.FavoriteUsers
import com.dicoding.proyekakhir.model.adapter.GithubUserAdapter
import com.dicoding.proyekakhir.model.data.GithubUser
import com.dicoding.proyekakhir.viewmodel.UserFavoriteViewModel
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class UserFavoriteActivity : AppCompatActivity() {

    private lateinit var githubUserAdapter: GithubUserAdapter
    private lateinit var userFavoriteViewModel: UserFavoriteViewModel
    private lateinit var binding: ActivityUserFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        githubUserAdapter = GithubUserAdapter()
        with(githubUserAdapter) {
            notifyDataSetChanged()
            setOnItemClick(object : GithubUserAdapter.OnItemClick {
                override fun onItemClicked(data: GithubUser) {
                    val intent = Intent(this@UserFavoriteActivity, UserDetailsActivity::class.java)
                    with(intent) {
                        putExtra(UserDetailsActivity.GITHUB_USERNAME, data.login)
                        putExtra(UserDetailsActivity.GITHUB_ID, data.id)
                        putExtra(UserDetailsActivity.GITHUB_AVATAR, data.avatar_url)
                    }
                    startActivity(intent)
                    slideToLeft()
                }
            })
        }

        userFavoriteViewModel = ViewModelProvider(this).get(UserFavoriteViewModel::class.java)
        userFavoriteViewModel.getListFavoriteUsers()?.observe(this, {
            if (it != null) {
                val mapList = mapList(it)
                githubUserAdapter.setGithubUser(mapList)
            }
        })

        with(binding) {
            btnArrowBack.setOnClickListener {
                finish()
                onBackPressed()
            }

            rvFavoriteUser.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@UserFavoriteActivity)
                adapter = githubUserAdapter

                addItemDecoration(
                    DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                )

                OverScrollDecoratorHelper.setUpOverScroll(
                    rvFavoriteUser,
                    OverScrollDecoratorHelper.ORIENTATION_VERTICAL
                )
            }
        }
    }

    override fun onBackPressed() {
        finish()
        slideToRight()
    }

    private fun mapList(favoriteUsers: List<FavoriteUsers>): ArrayList<GithubUser> {
        val userList = ArrayList<GithubUser>()
        for (users in favoriteUsers) {
            val mapUser = GithubUser(users.login, users.id, users.avatar_url)
            userList.add(mapUser)
        }
        return userList
    }

    private fun slideToRight() {
        overridePendingTransition(
            R.anim.slide_from_left_animation,
            R.anim.slide_to_right_animation
        )
    }

    private fun slideToLeft() {
        overridePendingTransition(
            R.anim.slide_from_right_animation,
            R.anim.slide_to_left_animation
        )
    }
}