package com.dicoding.proyekakhir.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.proyekakhir.R
import com.dicoding.proyekakhir.databinding.FragmentUserFollowingBinding
import com.dicoding.proyekakhir.model.adapter.UserFollowingAdapter
import com.dicoding.proyekakhir.view.ui.UserDetailsActivity
import com.dicoding.proyekakhir.viewmodel.UserFollowingViewModel
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class UserFollowingFragment : Fragment(R.layout.fragment_user_following) {

    private var _binding: FragmentUserFollowingBinding? = null
    private val binding get() = _binding!!

    private lateinit var userFollowingAdapter: UserFollowingAdapter
    private lateinit var userFollowingViewModel: UserFollowingViewModel
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentUserFollowingBinding.bind(view)
        username = arguments?.getString(UserDetailsActivity.GITHUB_USERNAME).toString()

        userFollowingAdapter = UserFollowingAdapter()
        userFollowingAdapter.notifyDataSetChanged()

        with(binding) {
            rvGithubUser.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(activity)
                adapter = userFollowingAdapter

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
            userFollowingViewModel = ViewModelProvider(this@UserFollowingFragment, viewModelProvider).get(UserFollowingViewModel::class.java)
            with(userFollowingViewModel) {
                setUserFollowing(username)
                getUserFollowing().observe(viewLifecycleOwner, {
                    if (it != null) {
                        userFollowingAdapter.setGithubUser(it)
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