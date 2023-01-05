/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.model

import com.giussepr.mubi.domain.model.SeasonDetail

data class SeasonDetailsDTO(
  val name: String,
  val episodes: List<EpisodeDTO>
) {
  fun toDomainSeasonDetail() = SeasonDetail(
    seasonName = name,
    episodes = episodes.map { it.toDomainEpisode() }
  )
}
