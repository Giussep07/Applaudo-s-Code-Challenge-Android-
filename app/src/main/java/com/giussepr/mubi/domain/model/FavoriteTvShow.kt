/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.domain.model

data class FavoriteTvShow(
  val id: Int,
  val backdropPath: String?,
  val firstAirDate: String?,
  val name: String,
  val originalLanguage: String,
  val originalName: String,
  val overview: String,
  val popularity: Double,
  val posterPath: String?,
  val voteAverage: Double,
  val voteCount: Int
)
