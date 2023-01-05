/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.model

import com.giussepr.mubi.domain.model.Episode
import com.google.gson.annotations.SerializedName

data class EpisodeDTO(
  @SerializedName("air_date")
  val airDate: String?,
  @SerializedName("episode_number")
  val episodeNumber: Int,
  val id: Int,
  val name: String,
  val overview: String,
  @SerializedName("production_code")
  val productionCode: String,
  val runtime: Int,
  @SerializedName("season_number")
  val seasonNumber: Int,
  @SerializedName("show_id")
  val showId: Int,
  @SerializedName("still_path")
  val stillPath: String?,
  @SerializedName("vote_average")
  val voteAverage: Double,
  @SerializedName("vote_count")
  val voteCount: Int,
) {
  fun toDomainEpisode(): Episode {
    return Episode(
      airDate = airDate,
      episodeNumber = episodeNumber,
      id = id,
      name = name,
      overview = overview,
      productionCode = productionCode,
      runtime = runtime,
      seasonNumber = seasonNumber,
      showId = showId,
      stillPath = stillPath,
      voteAverage = voteAverage,
      voteCount = voteCount,
    )
  }
}
