package com.dicoding.proyekakhir.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.proyekakhir.R
import com.dicoding.proyekakhir.databinding.ItemRowFollowBinding
import com.dicoding.proyekakhir.model.data.GithubUser

class UserFollowingAdapter : RecyclerView.Adapter<UserFollowingAdapter.GithubUserViewHolder>() {

    private val mUserFollowing = ArrayList<GithubUser>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GithubUserViewHolder {
        val binding = ItemRowFollowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return GithubUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) {
        val binding = ItemRowFollowBinding.bind(holder.itemView)
        with(binding) {
            container.apply {
                when (position) {
                    mUserFollowing.size - 1 -> setBackgroundResource(R.drawable.ripple_bottom_bg_white)
                    else -> setBackgroundResource(R.drawable.ripple_bg_white)
                }
            }
        }

        holder.bind(mUserFollowing[position])
    }

    override fun getItemCount(): Int = mUserFollowing.size

    fun setGithubUser(users: ArrayList<GithubUser>) {
        with(mUserFollowing) {
            clear()
            addAll(users)
        }
        notifyDataSetChanged()
    }

    inner class GithubUserViewHolder(private val binding: ItemRowFollowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(githubUser: GithubUser) {
            with(binding) {
                Glide.with(itemView)
                    .load(githubUser.avatar_url)
                    .into(imgAvatar)
                tvLogin.text = githubUser.login
            }
        }
    }

}