/*
 * Created by Giussep Ricardo on 05/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.database.entity

interface TvShowEntity {
  val tvShowId: Int
  val backdropPath: String
  val firstAirDate: String
  val name: String
  val originalLanguage: String
  val originalName: String
  val overview: String
  val popularity: Double
  val posterPath: String
  val voteAverage: Double
  val voteCount: Int
  val imageUrl: String
  val detailImageUrl: String
}
