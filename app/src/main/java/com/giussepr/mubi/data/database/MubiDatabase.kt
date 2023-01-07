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
import com.giussepr.mubi.data.database.dao.airingtoday.AiringTodayTvShowDao
import com.giussepr.mubi.data.database.dao.airingtoday.AiringTodayTvShowRemoteKeyDao
import com.giussepr.mubi.data.database.dao.ontvshow.OnTvShowDao
import com.giussepr.mubi.data.database.dao.ontvshow.OnTvShowRemoteKeyDao
import com.giussepr.mubi.data.database.dao.populartvshow.PopularTvShowDao
import com.giussepr.mubi.data.database.dao.populartvshow.PopularTvShowRemoteKeyDao
import com.giussepr.mubi.data.database.entity.FavoriteTvShowEntity
import com.giussepr.mubi.data.database.entity.airingtoday.AiringTodayTvShowEntity
import com.giussepr.mubi.data.database.entity.airingtoday.AiringTodayTvShowRemoteKey
import com.giussepr.mubi.data.database.entity.ontvshow.OnTvShowEntity
import com.giussepr.mubi.data.database.entity.ontvshow.OnTvShowRemoteKey
import com.giussepr.mubi.data.database.entity.populartvshow.PopularTvShowEntity
import com.giussepr.mubi.data.database.entity.populartvshow.PopularTvShowRemoteKey
import com.giussepr.mubi.data.database.entity.topratedtvshow.TopRatedTvShowEntity
import com.giussepr.mubi.data.database.entity.topratedtvshow.TopRatedTvShowRemoteKey
import com.giussepr.mubi.data.database.migrations.Migration1
import com.giussepr.mubi.data.database.migrations.Migration2
import com.giussepr.mubi.data.database.migrations.Migration3

@Database(
  entities = [
    FavoriteTvShowEntity::class,
    TopRatedTvShowRemoteKey::class,
    TopRatedTvShowEntity::class,
    PopularTvShowEntity::class,
    PopularTvShowRemoteKey::class,
    OnTvShowEntity::class,
    OnTvShowRemoteKey::class,
    AiringTodayTvShowEntity::class,
    AiringTodayTvShowRemoteKey::class],
  version = 6,
  exportSchema = true
)
abstract class MubiDatabase : RoomDatabase() {

  abstract fun favoriteTvShowDao(): FavoriteTvShowDao

  abstract fun tvShowRemoteKeyDao(): TopRatedTvShowRemoteKeyDao

  abstract fun topRatedTvShowDao(): TopRatedTvShowDao

  abstract fun popularTvShowDao(): PopularTvShowDao

  abstract fun popularTvShowRemoteKeyDao(): PopularTvShowRemoteKeyDao

  abstract fun onTvShowDao(): OnTvShowDao

  abstract fun onTvShowRemoteKeyDao(): OnTvShowRemoteKeyDao

  abstract fun airingTodayTvShowDao(): AiringTodayTvShowDao

  abstract fun airingTodayTvShowRemoteKeyDao(): AiringTodayTvShowRemoteKeyDao

  companion object {
    val MIGRATIONS = arrayOf(Migration1(), Migration2(), Migration3())
  }

}
