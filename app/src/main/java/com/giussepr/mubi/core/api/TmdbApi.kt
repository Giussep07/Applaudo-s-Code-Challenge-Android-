/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.core.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {

  @GET("tv/popular")
  suspend fun getPopularTvShows(
    @Query("api_key") apiKey: String,
    @Query("language") language: String = "en-US",
    @Query("page") page: Int = 1
  ): Response<String>
}
