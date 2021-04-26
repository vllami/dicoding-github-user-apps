package com.dicoding.proyekakhir.view.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.proyekakhir.R
import com.dicoding.proyekakhir.databinding.ActivityUserDetailsBinding
import com.dicoding.proyekakhir.model.adapter.SectionsPagerAdapter
import com.dicoding.proyekakhir.viewmodel.UserDetailsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.ln
import kotlin.math.pow

class UserDetailsActivity : AppCompatActivity() {

    companion object {
        const val GITHUB_USERNAME = "github_username"
        const val GITHUB_ID = "id"
        const val GITHUB_AVATAR = "avatar"
    }

    private lateinit var userDetailsViewModel: UserDetailsViewModel
    private lateinit var binding: ActivityUserDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(GITHUB_USERNAME)
        val id = intent.getIntExtra(GITHUB_ID, 0)
        val avatar = intent.getStringExtra(GITHUB_AVATAR)

        val saveDataUser = Bundle()
        saveDataUser.putString(GITHUB_USERNAME, username)

        userDetailsViewModel = ViewModelProvider(this).get(UserDetailsViewModel::class.java)
        with(binding) {
            with(userDetailsViewModel) {
                showLoading(true)
                setUserDetails(username.toString())
                getUserDetails().observe(this@UserDetailsActivity, {
                    val nullData = resources.getString(R.string.null_data)

                    if (it != null) {
                        Glide.with(this@UserDetailsActivity).load(it.avatar_url).into(imgAvatar)
                        tvName.text = it.name ?: nullData
                        tvUsername.text = it.login
                        tvCompany.text = it.company ?: nullData
                        tvLocation.text = it.location ?: nullData
                        tvRepository.text = shortNumberDisplay(it.public_repos.toLong())
                        showLoading(false)
                    }
                })
            }

            btnArrowBack.setOnClickListener {
                finish()
                onBackPressed()
            }

            var checkFavoriteUser = false
            CoroutineScope(Dispatchers.IO).launch {
                val countFavoriteUser = userDetailsViewModel.checkFavoriteUsers(id)
                withContext(Dispatchers.Main) {
                    if (countFavoriteUser != null) {
                        btnFavorite.apply {
                            if (countFavoriteUser > 0) {
                                isChecked = true
                                checkFavoriteUser = true
                            } else {
                                isChecked = false
                                checkFavoriteUser = false
                            }
                        }
                    }
                }
            }

            btnFavorite.setOnClickListener {
                checkFavoriteUser = !checkFavoriteUser
                val add = resources.getString(R.string.add_favorite)
                val remove = resources.getString(R.string.remove_favorite)
                with(userDetailsViewModel) {
                    btnFavorite.apply {
                        Log.d("btnFavorite", id.toString())
                        if (checkFavoriteUser) {
                            addFavoriteUsers(username.toString(), id, avatar.toString())
                            Snackbar.make(it, add, Snackbar.LENGTH_SHORT)
                                .setTextColor(context.getColor(R.color.colorWhite))
                                .setBackgroundTint(context.getColor(R.color.colorDarkGray))
                                .setActionTextColor(context.getColor(R.color.colorWhite))
                                .setAction(R.string.see) {
                                    val intent = Intent(this@UserDetailsActivity, UserFavoriteActivity::class.java)
                                    startActivity(intent)
                                }
                                .show()
                        } else {
                            removeFavoriteUsers(id)
                            Snackbar.make(it, remove, Snackbar.LENGTH_SHORT)
                                .setTextColor(context.getColor(R.color.colorWhite))
                                .setBackgroundTint(context.getColor(R.color.colorDarkGray))
                                .show()
                            isChecked = checkFavoriteUser
                        }
                    }
                }
            }

            val sectionsPagerAdapter = SectionsPagerAdapter(this@UserDetailsActivity, supportFragmentManager, saveDataUser)
            viewPager.adapter = sectionsPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
    }

    override fun onBackPressed() {
        finish()
        slideToRight()
    }

    private fun shortNumberDisplay(format: Long): String {
        if (format < 1000) return "" + format

        val count = (ln(format.toDouble()) / ln(1000.0)).toInt()
        return String.format("%.1f%c", format / 1000.0.pow(count.toDouble()), "KMBTPE"[count - 1])
    }

    private fun showLoading(state: Boolean) {
        with(binding.loading) {
            visibility = when {
                state -> View.VISIBLE
                else -> View.GONE
            }
        }
    }

    private fun slideToRight() {
        overridePendingTransition(
            R.anim.slide_from_left_animation,
            R.anim.slide_to_right_animation
        )
    }
}