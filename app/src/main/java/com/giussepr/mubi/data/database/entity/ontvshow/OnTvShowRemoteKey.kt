/*
 * Created by Giussep Ricardo on 05/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.database.entity.ontvshow

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "on_tv_show_remote_key")
data class OnTvShowRemoteKey(
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,
  @ColumnInfo(name = "tv_show_id")
  val tvShowId: Int,
  val nextKey: Int?,
  val prevKey: Int?,
)
