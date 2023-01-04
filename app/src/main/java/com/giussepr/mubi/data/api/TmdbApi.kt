/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.api

import com.giussepr.mubi.data.model.TvShowResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {

  @GET("tv/top_rated")
  suspend fun getTopRatedTvShows(
    @Query("api_key") apiKey: String,
    @Query("page") page: Int,
    @Query("language") language: String = "en-US"
  ): Response<TvShowResponseDTO>

  @GET("tv/popular")
  suspend fun getPopularTvShows(
    @Query("api_key") apiKey: String,
    @Query("page") page: Int,
    @Query("language") language: String = "en-US"
  ): Response<TvShowResponseDTO>
}
