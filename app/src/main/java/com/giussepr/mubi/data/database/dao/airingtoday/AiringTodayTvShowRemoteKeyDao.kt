/*
 * Created by Giussep Ricardo on 05/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.database.dao.airingtoday

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giussepr.mubi.data.database.entity.airingtoday.AiringTodayTvShowRemoteKey

@Dao
interface AiringTodayTvShowRemoteKeyDao {

  @Query("SELECT * FROM airing_today_tv_show_remote_key WHERE tv_show_id = :tvShowId")
  suspend fun getRemoteKeyByTvShowId(tvShowId: Int): AiringTodayTvShowRemoteKey?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addAllRemoteKeys(remoteKeys: List<AiringTodayTvShowRemoteKey>)

  @Query("DELETE FROM airing_today_tv_show_remote_key")
  suspend fun deleteAllRemoteKeys()
}
