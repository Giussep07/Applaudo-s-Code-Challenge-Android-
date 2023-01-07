/*
 * Created by Giussep Ricardo on 05/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.database.dao.ontvshow

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.giussepr.mubi.data.database.entity.ontvshow.OnTvShowEntity

@Dao
interface OnTvShowDao {

  @Query("SELECT * FROM on_tv_show")
  fun getAll(): PagingSource<Int, OnTvShowEntity>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(topRatedTvShowList: List<OnTvShowEntity>)

  @Query("DELETE FROM on_tv_show")
  suspend fun deleteAll()
}
