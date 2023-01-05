/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giussepr.mubi.data.database.entity.FavoriteTvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteTvShowDao {

  @Query("SELECT * FROM favorite_tv_show")
  fun getAll(): Flow<List<FavoriteTvShowEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(favoriteTvShow: FavoriteTvShowEntity)

  @Query("SELECT * FROM favorite_tv_show WHERE id = :tvShowId")
  suspend fun getFavoriteTvShowById(tvShowId: Int): FavoriteTvShowEntity?

  @Query("DELETE FROM favorite_tv_show WHERE id = :tvShowId")
  suspend fun deleteById(tvShowId: Int)
}
