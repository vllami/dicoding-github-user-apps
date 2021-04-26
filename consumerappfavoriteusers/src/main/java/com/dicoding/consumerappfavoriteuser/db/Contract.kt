package com.dicoding.consumerappfavoriteuser.db

import android.net.Uri
import android.provider.BaseColumns

object Contract {

    const val AUTHOR = "com.dicoding.proyekakhir"
    const val SCHEME = "content"

    internal class Column: BaseColumns {

        companion object {
            private const val TABLE_NAME = "favorite_users"

            const val USERNAME = "login"
            const val ID = "id"
            const val AVATAR_URL = "avatar_url"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME).authority(AUTHOR).appendPath(TABLE_NAME).build()
        }

    }

}