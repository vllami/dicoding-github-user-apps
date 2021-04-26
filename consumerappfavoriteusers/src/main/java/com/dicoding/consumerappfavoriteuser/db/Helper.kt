package com.dicoding.consumerappfavoriteuser.db

import android.database.Cursor
import com.dicoding.consumerappfavoriteuser.model.data.GithubUser

object Helper {

    fun dataCursorList(cursor: Cursor?): ArrayList<GithubUser> {
        val favoriteUsersList = ArrayList<GithubUser>()

        cursor?.apply {
            while (moveToNext()) {
                val username = getString(getColumnIndexOrThrow(Contract.Column.USERNAME))
                val id = getInt(getColumnIndexOrThrow(Contract.Column.ID))
                val avatar = getString(getColumnIndexOrThrow(Contract.Column.AVATAR_URL))
                favoriteUsersList.add(GithubUser(username, id, avatar))
            }
        }
        return favoriteUsersList
    }

}