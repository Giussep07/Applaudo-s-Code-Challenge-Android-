/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.model

import com.giussepr.mubi.domain.model.TvShow
import com.google.gson.annotations.SerializedName

data class TvShowDTO(
  val id: Int,
  @SerializedName("backdrop_path")
  val backdropPath: String?,
  @SerializedName("first_air_date")
  val firstAirDate: String?,
  @SerializedName("genre_ids")
  val genreIds: List<Int>,
  val name: String,
  @SerializedName("origin_country")
  val originCountry: List<String>,
  @SerializedName("original_language")
  val originalLanguage: String,
  @SerializedName("original_name")
  val originalName: String,
  val overview: String,
  val popularity: Double,
  @SerializedName("poster_path")
  val posterPath: String?,
  @SerializedName("vote_average")
  val voteAverage: Double,
  @SerializedName("vote_count")
  val voteCount: Int
) {

  fun toDomainTvShow(): TvShow {
    return TvShow(
      id = id,
      backdropPath = backdropPath ?: posterPath ?: "",
      firstAirDate = firstAirDate ?: "",
      genreIds = genreIds,
      name = name,
      originCountry = originCountry,
      originalLanguage = originalLanguage,
      originalName = originalName,
      overview = overview,
      popularity = popularity,
      posterPath = posterPath ?: "",
      voteAverage = voteAverage,
      voteCount = voteCount
    )
  }
}
