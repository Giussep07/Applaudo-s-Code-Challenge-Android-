/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.domain.repository

import androidx.paging.PagingData
import com.giussepr.mubi.domain.model.*
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {
  fun getTopRatedTvShows(): Flow<PagingData<TvShow>>
  fun getPopularTvShows(): Flow<PagingData<TvShow>>
  fun getOnTvShows(): Flow<PagingData<TvShow>>
  fun getAiringTodayTvShows(): Flow<PagingData<TvShow>>
  fun searchTvShowsByTerm(searchTerm: String): Flow<PagingData<TvShow>>
  fun getTvShowDetails(tvShowId: Int): Flow<Result<TvShowDetail>>
  fun getTvShowSeasonDetails(tvShowId: Int, seasonNumber: Int): Flow<Result<SeasonDetail>>
  fun getLocalFavoriteTvShows(): Flow<Result<List<FavoriteTvShow>>>
  fun saveLocalFavoriteTvShow(favoriteTvShow: FavoriteTvShow): Flow<Result<Boolean>>
  fun removeLocalFavoriteTvShow(tvShowId: Int): Flow<Result<Boolean>>
  fun getLocalFavoriteShowById(tvShowId: Int): Flow<Result<FavoriteTvShow?>>
}
