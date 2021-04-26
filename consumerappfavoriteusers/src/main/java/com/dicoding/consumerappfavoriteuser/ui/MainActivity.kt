package com.dicoding.consumerappfavoriteuser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.consumerappfavoriteuser.databinding.ActivityMainBinding
import com.dicoding.consumerappfavoriteuser.model.adapter.GithubUserAdapter
import com.dicoding.consumerappfavoriteuser.viewmodel.UserFavoriteViewModel
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class MainActivity : AppCompatActivity() {

    private lateinit var githubUserAdapter: GithubUserAdapter
    private lateinit var favoriteViewModel: UserFavoriteViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        githubUserAdapter = GithubUserAdapter()
        githubUserAdapter.notifyDataSetChanged()

        with(binding) {
            rvFavoriteUser.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = githubUserAdapter

                addItemDecoration(
                    DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                )
            }

            OverScrollDecoratorHelper.setUpOverScroll(
                rvFavoriteUser,
                OverScrollDecoratorHelper.ORIENTATION_VERTICAL
            )
        }

        favoriteViewModel = ViewModelProvider(this).get(UserFavoriteViewModel::class.java)
        with(favoriteViewModel) {
            setFavoriteUsers(this@MainActivity)
            getFavoriteUsers().observe(this@MainActivity, {
                if (it != null) githubUserAdapter.setGithubUser(it)
            })
        }
    }
}