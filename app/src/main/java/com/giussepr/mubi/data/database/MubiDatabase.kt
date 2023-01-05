/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.giussepr.mubi.data.database.dao.FavoriteTvShowDao
import com.giussepr.mubi.data.database.entity.FavoriteTvShowEntity

@Database(
  entities = [FavoriteTvShowEntity::class],
  version = 1,
  exportSchema = true
)
abstract class MubiDatabase : RoomDatabase() {

  abstract fun favoriteTvShowDao(): FavoriteTvShowDao

}
