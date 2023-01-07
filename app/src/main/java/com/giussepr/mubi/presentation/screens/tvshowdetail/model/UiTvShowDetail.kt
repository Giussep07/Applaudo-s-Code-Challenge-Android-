/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.screens.tvshowdetail.model

import com.giussepr.mubi.domain.model.FavoriteTvShow

data class UiTvShowDetail(
  val id: Int,
  val imageUrl: String,
  val originalName: String,
  val name: String,
  val averageRate: Double,
  val overview: String,
) {
  fun toDomainFavoriteTvShow(): FavoriteTvShow {
    return FavoriteTvShow(
      id = id,
      imageUrl = imageUrl,
      name = name,
      originalName = originalName,
      overview = overview,
      voteAverage = averageRate,
    )
  }
}
