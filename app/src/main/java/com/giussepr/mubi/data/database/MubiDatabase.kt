/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.giussepr.mubi.data.database.dao.FavoriteTvShowDao
import com.giussepr.mubi.data.database.dao.TopRatedTvShowDao
import com.giussepr.mubi.data.database.dao.TopRatedTvShowRemoteKeyDao
import com.giussepr.mubi.data.database.entity.FavoriteTvShowEntity
import com.giussepr.mubi.data.database.entity.TopRatedTvShowEntity
import com.giussepr.mubi.data.database.entity.TopRatedTvShowRemoteKey
import com.giussepr.mubi.data.database.migrations.Migration1
import com.giussepr.mubi.data.database.migrations.Migration2
import com.giussepr.mubi.data.database.migrations.Migration3

@Database(
  entities = [FavoriteTvShowEntity::class, TopRatedTvShowRemoteKey::class, TopRatedTvShowEntity::class],
  version = 4,
  exportSchema = true
)
abstract class MubiDatabase : RoomDatabase() {

  abstract fun favoriteTvShowDao(): FavoriteTvShowDao

  abstract fun tvShowRemoteKeyDao(): TopRatedTvShowRemoteKeyDao

  abstract fun topRatedTvShowDao(): TopRatedTvShowDao

  companion object {
    val MIGRATIONS = arrayOf(Migration1(), Migration2(), Migration3())
  }

}
