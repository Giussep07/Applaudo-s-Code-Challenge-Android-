/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.domain.model

import com.giussepr.mubi.data.database.entity.FavoriteTvShowEntity
import com.giussepr.mubi.presentation.screens.tvshowdetail.model.UiTvShowDetail

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

  fun toUiTvShowDetail(): UiTvShowDetail {
    return UiTvShowDetail(
      id = id,
      imageUrl = imageUrl,
      originalName = originalName,
      name = name,
      averageRate = voteAverage,
      overview = overview,
    )
  }
}
