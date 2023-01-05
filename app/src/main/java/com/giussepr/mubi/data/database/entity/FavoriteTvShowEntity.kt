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
  @ColumnInfo(name = "backdrop_path")
  val backdropPath: String?,
  @ColumnInfo(name = "first_air_date")
  val firstAirDate: String?,
  val name: String,
  @ColumnInfo(name = "original_language")
  val originalLanguage: String,
  @ColumnInfo(name = "original_name")
  val originalName: String,
  val overview: String,
  val popularity: Double,
  @ColumnInfo(name = "poster_path")
  val posterPath: String?,
  @ColumnInfo(name = "vote_average")
  val voteAverage: Double,
  @ColumnInfo(name = "vote_count")
  val voteCount: Int
) {
  fun toDomainFavoriteTvShow(): FavoriteTvShow {
    return FavoriteTvShow(
      id = id,
      backdropPath = backdropPath,
      firstAirDate = firstAirDate,
      name = name,
      originalLanguage = originalLanguage,
      originalName = originalName,
      overview = overview,
      popularity = popularity,
      posterPath = posterPath,
      voteAverage = voteAverage,
      voteCount = voteCount
    )
  }
}
