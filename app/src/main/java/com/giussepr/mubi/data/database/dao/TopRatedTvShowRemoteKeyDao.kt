/*
 * Created by Giussep Ricardo on 05/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giussepr.mubi.data.database.entity.topratedtvshow.TopRatedTvShowRemoteKey

@Dao
interface TopRatedTvShowRemoteKeyDao {

  @Query("SELECT * FROM top_rated_tv_show_remote_key WHERE tv_show_id = :tvShowId")
  suspend fun getRemoteKeyByTvShowId(tvShowId: Int): TopRatedTvShowRemoteKey?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun addAllRemoteKeys(remoteKeys: List<TopRatedTvShowRemoteKey>)

  @Query("DELETE FROM top_rated_tv_show_remote_key")
  suspend fun deleteAllRemoteKeys()
}
