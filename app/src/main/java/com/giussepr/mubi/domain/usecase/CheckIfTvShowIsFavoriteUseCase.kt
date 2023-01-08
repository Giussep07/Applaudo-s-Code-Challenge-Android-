/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.domain.usecase

import com.giussepr.mubi.domain.error.DomainException
import com.giussepr.mubi.domain.model.fold
import com.giussepr.mubi.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.giussepr.mubi.domain.model.Result

class CheckIfTvShowIsFavoriteUseCase @Inject constructor(
  private val tvShowRepository: TvShowRepository,
) {

  operator fun invoke(tvShowId: Int): Flow<Result<Boolean>> {
    return tvShowRepository.getLocalFavoriteShowById(tvShowId).map { result ->
      result.fold(
        onSuccess = { Result.Success(it != null) },
        onFailure = { Result.Error(DomainException(it.message)) }
      )
    }
  }

}
