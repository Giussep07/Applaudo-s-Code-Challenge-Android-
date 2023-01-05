/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.domain.model

data class Episode(
  val airDate: String?,
  val episodeNumber: Int,
  val id: Int,
  val name: String,
  val overview: String,
  val productionCode: String,
  val runtime: Int,
  val seasonNumber: Int,
  val showId: Int,
  val stillPath: String?,
  val voteAverage: Double,
  val voteCount: Int,
  val imageUrl: String = "$IMAGE_BASE_URL${stillPath}",
) {
  companion object {
    private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w342"
  }
}
