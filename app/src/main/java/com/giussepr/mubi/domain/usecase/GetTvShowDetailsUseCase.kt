/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.domain.usecase

import com.giussepr.mubi.domain.repository.TvShowRepository
import javax.inject.Inject

class GetTvShowDetailsUseCase @Inject constructor(
  private val tvShowRepository: TvShowRepository,
) {
  operator fun invoke(tvShowId: Int) = tvShowRepository.getTvShowDetails(tvShowId)
}
