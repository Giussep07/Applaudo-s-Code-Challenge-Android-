/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.domain.model

import com.giussepr.mubi.data.database.entity.FavoriteTvShowEntity

data class FavoriteTvShow(
  val id: Int,
  val imageUrl: String,
  val originalName: String,
  val name: String,
  val overview: String,
  val voteAverage: Double,
) {
  fun toDataFavoriteTvShow(): FavoriteTvShowEntity {
    return FavoriteTvShowEntity(
      id = id,
      imageUrl = imageUrl,
      name = name,
      originalName = originalName,
      overview = overview,
      voteAverage = voteAverage,
    )
  }
}
