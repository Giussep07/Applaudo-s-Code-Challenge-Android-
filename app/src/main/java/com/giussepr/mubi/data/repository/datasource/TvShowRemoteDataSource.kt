/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.repository.datasource

import com.giussepr.mubi.data.model.TvShowResponseDTO
import retrofit2.Response

interface TvShowRemoteDataSource {
  suspend fun getTopRatedTvShows(): Response<TvShowResponseDTO>
}
