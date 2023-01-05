/*
 * Created by Giussep Ricardo on 05/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

/*
 * Created by Giussep Ricardo on 05/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.database.entity.airingtoday

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.giussepr.mubi.data.database.entity.TvShowEntity
import com.giussepr.mubi.domain.model.TvShow

@Entity(tableName = "airing_today_tv_show")
data class AiringTodayTvShowEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Int = 0,
  override val tvShowId: Int,
  @ColumnInfo(name = "backdrop_path")
  override val backdropPath: String,
  @ColumnInfo(name = "first_air_date")
  override val firstAirDate: String,
  override val name: String,
  @ColumnInfo(name = "original_language")
  override val originalLanguage: String,
  @ColumnInfo(name = "original_name")
  override val originalName: String,
  override val overview: String,
  override val popularity: Double,
  @ColumnInfo(name = "poster_path")
  override val posterPath: String,
  @ColumnInfo(name = "vote_average")
  override val voteAverage: Double,
  @ColumnInfo(name = "vote_count")
  override val voteCount: Int,
  @ColumnInfo(name = "image_url")
  override val imageUrl: String,
  @ColumnInfo(name = "detail_image_url")
  override val detailImageUrl: String
) : TvShowEntity {

  fun toDomainTvShow(): TvShow {
    return TvShow(
      id = tvShowId,
      backdropPath = backdropPath,
      firstAirDate = firstAirDate,
      name = name,
      originalLanguage = originalLanguage,
      originalName = originalName,
      overview = overview,
      popularity = popularity,
      posterPath = posterPath,
      voteAverage = voteAverage,
      voteCount = voteCount,
      imageUrl = imageUrl,
      detailImageUrl = detailImageUrl,
      genreIds = emptyList(),
      originCountry = emptyList()
    )
  }
}
