/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giussepr.mubi.domain.model.FavoriteTvShow

@Entity(tableName = "favorite_tv_show")
data class FavoriteTvShowEntity(
  @PrimaryKey
  val id: Int,
  @ColumnInfo(name = "image_url")
  val imageUrl: String,
  val name: String,
  @ColumnInfo(name = "original_name")
  val originalName: String,
  val overview: String,
  @ColumnInfo(name = "vote_average")
  val voteAverage: Double,
) {
  fun toDomainFavoriteTvShow(): FavoriteTvShow {
    return FavoriteTvShow(
      id = id,
      imageUrl = imageUrl,
      name = name,
      originalName = originalName,
      overview = overview,
      voteAverage = voteAverage,
    )
  }
}
