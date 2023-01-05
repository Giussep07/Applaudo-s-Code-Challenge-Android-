/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.domain.model

import com.giussepr.mubi.presentation.screens.tvshowdetail.model.UiTvShowDetail


data class TvShow(
  val id: Int,
  val backdropPath: String,
  val firstAirDate: String,
  val genreIds: List<Int>,
  val name: String,
  val originCountry: List<String>,
  val originalLanguage: String,
  val originalName: String,
  val overview: String,
  val popularity: Double,
  val posterPath: String,
  val voteAverage: Double,
  val voteCount: Int,
  val imageUrl: String = "$IMAGE_BASE_URL$backdropPath",
  val detailImageUrl: String = "$IMAGE_BASE_URL$backdropPath",
) {

  fun toUiTvShowDetail(): UiTvShowDetail {
    return UiTvShowDetail(
      id = id,
      imageUrl = detailImageUrl,
      originalName = originalName,
      name = name,
      averageRate = voteAverage,
      overview = overview,
    )
  }

  companion object {
    private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w780"
  }
}
