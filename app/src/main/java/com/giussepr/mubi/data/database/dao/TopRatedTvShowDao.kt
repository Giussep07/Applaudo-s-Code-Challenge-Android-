/*
 * Created by Giussep Ricardo on 05/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giussepr.mubi.data.database.entity.topratedtvshow.TopRatedTvShowEntity

@Dao
interface TopRatedTvShowDao {

  @Query("SELECT * FROM top_rated_tv_show")
  fun getAll(): PagingSource<Int, TopRatedTvShowEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(topRatedTvShowList: List<TopRatedTvShowEntity>)

  @Query("DELETE FROM top_rated_tv_show")
  suspend fun deleteAll()
}
