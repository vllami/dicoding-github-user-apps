package com.dicoding.proyekakhir.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.dicoding.proyekakhir.db.FavoriteUsersDB
import com.dicoding.proyekakhir.db.room.FavoriteUsersDAO

class GithubUserProvider : ContentProvider() {

    companion object {
        private const val AUTHOR = "com.dicoding.proyekakhir"
        private const val TABLE_NAME = "favorite_users"
        private const val DATA_ID = 1

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHOR, TABLE_NAME, DATA_ID)
        }
    }

    private lateinit var favoriteUsersDAO: FavoriteUsersDAO

    override fun onCreate(): Boolean {
        val favoriteUsers = context?.let { FavoriteUsersDB.getFavoriteUsersDB(it)?.favoriteUsersDAO() }
        favoriteUsersDAO = if (favoriteUsers != null) favoriteUsers else throw KotlinNullPointerException()
        return false
    }

    override fun query(
        uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        when (uriMatcher.match(uri)) {
            DATA_ID -> {
                cursor = favoriteUsersDAO.consumerFavoriteUsers()
                if (context != null) cursor.setNotificationUri(context?.contentResolver, uri)
            }
            else -> cursor = null
        }
        return cursor
    }

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int = 0

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0
}