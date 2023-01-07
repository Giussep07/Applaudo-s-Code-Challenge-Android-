/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.domain.usecase

import com.giussepr.mubi.domain.model.FavoriteTvShow
import com.giussepr.mubi.domain.repository.TvShowRepository
import javax.inject.Inject

class SaveLocalFavoriteTvShowUseCase @Inject constructor(private val tvShowRepository: TvShowRepository) {

  operator fun invoke(favoriteTvShow: FavoriteTvShow) =
    tvShowRepository.saveLocalFavoriteTvShow(favoriteTvShow)
}

