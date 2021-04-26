package com.dicoding.proyekakhir.view.ui

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.proyekakhir.R
import com.dicoding.proyekakhir.databinding.ActivityMainBinding
import com.dicoding.proyekakhir.model.adapter.GithubUserAdapter
import com.dicoding.proyekakhir.model.data.GithubUser
import com.dicoding.proyekakhir.viewmodel.MainViewModel
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil

class MainActivity : AppCompatActivity() {

    private lateinit var githubUserAdapter: GithubUserAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        githubUserAdapter = GithubUserAdapter()
        with(githubUserAdapter) {
            notifyDataSetChanged()
            setOnItemClick(object : GithubUserAdapter.OnItemClick {
                override fun onItemClicked(data: GithubUser) {
                    val intent = Intent(this@MainActivity, UserDetailsActivity::class.java)
                    with(intent) {
                        data.apply {
                            putExtra(UserDetailsActivity.GITHUB_USERNAME, login)
                            putExtra(UserDetailsActivity.GITHUB_ID, id)
                            putExtra(UserDetailsActivity.GITHUB_AVATAR, avatar_url)
                        }
                    }
                    startActivity(intent)
                    slideToLeft()
                }
            })
        }

        with(binding) {
            rvGithubUser.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = githubUserAdapter

                addItemDecoration(
                    DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                )
            }

            etSearchUser.apply {
                setOnKeyListener { _, keyCode, event ->
                    if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                        searchUser()
                        showControl(true)
                        return@setOnKeyListener true
                    }
                    false
                }
            }

            btnClear.setOnClickListener {
                etSearchUser.text.clear()

                showControl(true)
                guide.visibility = View.VISIBLE
                loading.visibility = View.GONE
                UIUtil.showKeyboard(this@MainActivity, etSearchUser)
            }

            btnFavorite.setOnClickListener {
                val intent = Intent(this@MainActivity, UserFavoriteActivity::class.java)
                startActivity(intent)
                slideToLeft()
            }

            btnSettings.setOnClickListener {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
                slideToLeft()
            }

            OverScrollDecoratorHelper.setUpOverScroll(
                rvGithubUser,
                OverScrollDecoratorHelper.ORIENTATION_VERTICAL
            )
        }

        val viewModelProvider = ViewModelProvider.NewInstanceFactory()
        mainViewModel = ViewModelProvider(this, viewModelProvider).get(MainViewModel::class.java)
        mainViewModel.getSearch().observe(this) {
            githubUserAdapter.setGithubUser(it)

            if (it != null) {
                showControl(false)
                UIUtil.hideKeyboard(this@MainActivity)
            }
        }
    }

    private fun searchUser() {
        with(binding) {
            val searchQuery = etSearchUser.text.toString()

            if (searchQuery.isEmpty()) showControl(false)
            mainViewModel.setSearch(searchQuery)
        }
    }

    private fun showControl(state: Boolean) {
        with(binding) {
            when {
                state -> {
                    btnClear.visibility = View.GONE
                    guide.visibility = View.GONE
                    loading.visibility = View.VISIBLE
                    rvGithubUser.visibility = View.GONE
                }
                else -> {
                    btnClear.visibility = View.VISIBLE
                    guide.visibility = View.GONE
                    loading.visibility = View.GONE
                    rvGithubUser.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun slideToLeft() {
        overridePendingTransition(
            R.anim.slide_from_right_animation,
            R.anim.slide_to_left_animation
        )
    }
}