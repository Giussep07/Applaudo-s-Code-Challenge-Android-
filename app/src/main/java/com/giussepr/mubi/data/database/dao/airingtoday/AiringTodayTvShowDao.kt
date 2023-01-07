/*
 * Created by Giussep Ricardo on 05/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.database.dao.airingtoday

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giussepr.mubi.data.database.entity.airingtoday.AiringTodayTvShowEntity

@Dao
interface AiringTodayTvShowDao {

  @Query("SELECT * FROM airing_today_tv_show")
  fun getAll(): PagingSource<Int, AiringTodayTvShowEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(topRatedTvShowList: List<AiringTodayTvShowEntity>)

  @Query("DELETE FROM airing_today_tv_show")
  suspend fun deleteAll()
}
