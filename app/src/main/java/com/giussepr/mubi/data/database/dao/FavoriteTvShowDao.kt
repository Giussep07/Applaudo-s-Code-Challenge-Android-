/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.giussepr.mubi.data.database.entity.FavoriteTvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteTvShowDao {

  @Query("SELECT * FROM favorite_tv_show")
  fun getAll(): Flow<List<FavoriteTvShowEntity>>
}
