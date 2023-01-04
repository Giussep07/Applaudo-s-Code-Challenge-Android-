/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.domain.repository

import com.giussepr.mubi.domain.model.Result
import com.giussepr.mubi.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {
  fun getTopRatedTvShows(): Flow<Result<List<TvShow>>>
}
