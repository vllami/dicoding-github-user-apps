package com.dicoding.proyekakhir.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.proyekakhir.R
import com.dicoding.proyekakhir.databinding.FragmentUserFollowersBinding
import com.dicoding.proyekakhir.model.adapter.UserFollowersAdapter
import com.dicoding.proyekakhir.view.ui.UserDetailsActivity
import com.dicoding.proyekakhir.viewmodel.UserFollowersViewModel
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class UserFollowersFragment : Fragment(R.layout.fragment_user_followers) {

    private var _binding: FragmentUserFollowersBinding? = null
    private val binding get() = _binding as FragmentUserFollowersBinding

    private lateinit var userFollowersAdapter: UserFollowersAdapter
    private lateinit var userFollowersViewModel: UserFollowersViewModel
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentUserFollowersBinding.bind(view)
        username = arguments?.getString(UserDetailsActivity.GITHUB_USERNAME).toString()

        userFollowersAdapter = UserFollowersAdapter()
        userFollowersAdapter.notifyDataSetChanged()

        with(binding) {
            rvGithubUser.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(activity)
                adapter = userFollowersAdapter

                addItemDecoration(
                    DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
                )

                OverScrollDecoratorHelper.setUpOverScroll(
                    rvGithubUser,
                    OverScrollDecoratorHelper.ORIENTATION_VERTICAL
                )
            }

            showLoading(true)
            val viewModelProvider = ViewModelProvider.NewInstanceFactory()
            userFollowersViewModel = ViewModelProvider(this@UserFollowersFragment, viewModelProvider).get(UserFollowersViewModel::class.java)
            with(userFollowersViewModel) {
                setUserFollowers(username)
                getUserFollowers().observe(viewLifecycleOwner, {
                    if (it != null) {
                        userFollowersAdapter.setGithubUser(it)
                        showLoading(false)
                    }
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(state: Boolean) {
        with(binding.loading) {
            visibility = when {
                state -> View.VISIBLE
                else -> View.GONE
            }
        }
    }

}