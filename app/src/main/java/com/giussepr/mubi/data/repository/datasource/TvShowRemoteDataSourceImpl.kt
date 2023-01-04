/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.repository.datasource

import com.giussepr.mubi.data.api.TmdbApi
import com.giussepr.mubi.data.model.TvShowDetailsDTO
import com.giussepr.mubi.data.model.TvShowResponseDTO
import com.giussepr.mubi.presentation.util.Constants
import retrofit2.Response
import javax.inject.Named

class TvShowRemoteDataSourceImpl(
  private val tmdbApi: TmdbApi,
  @Named(Constants.API_KEY) private val apiKey: String
) : TvShowRemoteDataSource {

  override suspend fun getTopRatedTvShows(page: Int): Response<TvShowResponseDTO> =
    tmdbApi.getTopRatedTvShows(apiKey = apiKey, page = page)

  override suspend fun getPopularTvShows(page: Int): Response<TvShowResponseDTO> =
    tmdbApi.getPopularTvShows(apiKey = apiKey, page = page)

  override suspend fun getOnTvShows(page: Int): Response<TvShowResponseDTO> =
    tmdbApi.getOnTvShows(apiKey = apiKey, page = page)

  override suspend fun getAiringTodayTvShows(page: Int): Response<TvShowResponseDTO> =
    tmdbApi.getAiringTodayTvShows(apiKey = apiKey, page = page)

  override suspend fun searchTvShowsByTerm(searchTerm: String, page: Int): Response<TvShowResponseDTO> =
    tmdbApi.searchTvShowsByTerm(apiKey = apiKey, searchTerm = searchTerm, page = page)

  override suspend fun getTvShowDetails(tvShowId: Int): Response<TvShowDetailsDTO> =
    tmdbApi.getTvShowDetails(tvShowId = tvShowId, apiKey = apiKey)
}
