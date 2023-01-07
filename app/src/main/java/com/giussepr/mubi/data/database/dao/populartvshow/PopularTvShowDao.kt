/*
 * Created by Giussep Ricardo on 05/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.database.dao.populartvshow

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giussepr.mubi.data.database.entity.populartvshow.PopularTvShowEntity

@Dao
interface PopularTvShowDao {

  @Query("SELECT * FROM popular_tv_show")
  fun getAll(): PagingSource<Int, PopularTvShowEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(topRatedTvShowList: List<PopularTvShowEntity>)

  @Query("DELETE FROM popular_tv_show")
  suspend fun deleteAll()
}
