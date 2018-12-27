package com.apwdevs.footballMatch.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class FavoriteDatabaseHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteMatch.db", null, 1) {

    companion object {
        private var instance: FavoriteDatabaseHelper? = null
        fun getInstance(ctx: Context): FavoriteDatabaseHelper {
            if (instance == null) {
                instance = FavoriteDatabaseHelper(ctx)
            }
            return instance as FavoriteDatabaseHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            FavoriteData.TABLE_FAVORITE, true,
            FavoriteData.ID_UNIQUE to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteData.ID_LEAGUE to TEXT,
            FavoriteData.ID_EVENT to TEXT,
            FavoriteData.LEAGUE_NAME to TEXT,
            FavoriteData.DATE_EVENT to TEXT,
            FavoriteData.HOME_TEAM to TEXT,
            FavoriteData.HOME_SCORE to INTEGER,
            FavoriteData.AWAY_TEAM to TEXT,
            FavoriteData.AWAY_SCORE to INTEGER
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoriteData.TABLE_FAVORITE, true)
    }
}

val Context.database: FavoriteDatabaseHelper
    get() = FavoriteDatabaseHelper.getInstance(applicationContext)