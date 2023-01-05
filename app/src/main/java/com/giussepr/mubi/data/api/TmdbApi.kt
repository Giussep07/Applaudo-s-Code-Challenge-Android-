/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.api

import com.giussepr.mubi.data.model.SeasonDetailsDTO
import com.giussepr.mubi.data.model.TvShowDetailsDTO
import com.giussepr.mubi.data.model.TvShowResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
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

  @GET("tv/on_the_air")
  suspend fun getOnTvShows(
    @Query("api_key") apiKey: String,
    @Query("page") page: Int,
    @Query("language") language: String = "en-US"
  ): Response<TvShowResponseDTO>

  @GET("tv/airing_today")
  suspend fun getAiringTodayTvShows(
    @Query("api_key") apiKey: String,
    @Query("page") page: Int,
    @Query("language") language: String = "en-US"
  ): Response<TvShowResponseDTO>

  @GET("search/tv")
  suspend fun searchTvShowsByTerm(
    @Query("api_key") apiKey: String,
    @Query("page") page: Int,
    @Query("language") language: String = "en-US",
    @Query("query") searchTerm: String
  ): Response<TvShowResponseDTO>

  @GET("tv/{tv_id}")
  suspend fun getTvShowDetails(
    @Path("tv_id") tvShowId: Int,
    @Query("api_key") apiKey: String,
    @Query("language") language: String = "en-US"
  ): Response<TvShowDetailsDTO>

  @GET("tv/{tv_id}/season/{season_number}")
  suspend fun getSeasonDetails(
    @Path("tv_id") tvShowId: Int,
    @Path("season_number") seasonNumber: Int,
    @Query("api_key") apiKey: String,
    @Query("language") language: String = "en-US"
  ): Response<SeasonDetailsDTO>

}
