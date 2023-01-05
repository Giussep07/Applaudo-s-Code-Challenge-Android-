/*
 * Created by Giussep Ricardo on 05/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.database.entity.airingtoday

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airing_today_tv_show_remote_key")
data class AiringTodayTvShowRemoteKey(
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,
  @ColumnInfo(name = "tv_show_id")
  val tvShowId: Int,
  val nextKey: Int?,
  val prevKey: Int?,
)
