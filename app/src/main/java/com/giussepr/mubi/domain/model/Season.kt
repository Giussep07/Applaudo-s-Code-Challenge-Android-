/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.domain.model

data class Season(
  val id: Int,
  val airDate: String?,
  val episodeCount: Int,
  val name: String,
  val overview: String,
  val posterPath: String?,
  val seasonNumber: Int,
  val imageUrl: String = "$IMAGE_BASE_URL${posterPath}",
) {
  companion object {
    private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w342"
  }
}
