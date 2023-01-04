/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.model

import com.giussepr.mubi.domain.model.Season
import com.google.gson.annotations.SerializedName

data class SeasonDTO(
  @SerializedName("air_date")
  val airDate: String?,
  @SerializedName("episode_count")
  val episodeCount: Int,
  val id: Int,
  val name: String,
  val overview: String,
  @SerializedName("poster_path")
  val posterPath: String?,
  @SerializedName("season_number")
  val seasonNumber: Int,
) {
  fun toDomainSeason(): Season {
    return Season(
      airDate = airDate,
      episodeCount = episodeCount,
      id = id,
      name = name,
      overview = overview,
      posterPath = posterPath,
      seasonNumber = seasonNumber,
    )
  }
}
