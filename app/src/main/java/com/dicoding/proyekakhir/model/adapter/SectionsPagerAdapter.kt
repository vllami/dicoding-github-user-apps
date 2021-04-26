package com.dicoding.proyekakhir.model.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.proyekakhir.R
import com.dicoding.proyekakhir.view.fragment.UserFollowersFragment
import com.dicoding.proyekakhir.view.fragment.UserFollowingFragment

class SectionsPagerAdapter(private val context: Context, fragmentManager: FragmentManager, bundle: Bundle) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        @StringRes
        private val TABS_TITLE = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    private var mBundle: Bundle = bundle

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = UserFollowersFragment()
            1 -> fragment = UserFollowingFragment()
        }
        fragment?.arguments = this.mBundle
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence = context.resources.getString(TABS_TITLE[position])

}