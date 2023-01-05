/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.giussepr.mubi.data.model.ResponseErrorBody
import com.giussepr.mubi.data.paging.*
import com.giussepr.mubi.data.repository.datasource.TvShowRemoteDataSource
import com.giussepr.mubi.data.repository.datasource.local.TvShowLocalDataSource
import com.giussepr.mubi.domain.error.ApiException
import com.giussepr.mubi.domain.error.DomainException
import com.giussepr.mubi.domain.model.*
import com.giussepr.mubi.domain.repository.TvShowRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(
  private val tvShowRemoteDataSource: TvShowRemoteDataSource,
  private val tvShowLocalDataSource: TvShowLocalDataSource
) :
  TvShowRepository {

  override fun getTopRatedTvShows() = Pager(
    config = PagingConfig(pageSize = 20),
    pagingSourceFactory = { TopRatedTvShowPagingSource(tvShowRemoteDataSource) }
  ).flow

  override fun getPopularTvShows() = Pager(
    config = PagingConfig(pageSize = 20),
    pagingSourceFactory = { PopularTvShowPagingSource(tvShowRemoteDataSource) }
  ).flow

  override fun getOnTvShows() = Pager(
    config = PagingConfig(pageSize = 20),
    pagingSourceFactory = { OnTvShowsPagingSource(tvShowRemoteDataSource) }
  ).flow

  override fun getAiringTodayTvShows() = Pager(
    config = PagingConfig(pageSize = 20),
    pagingSourceFactory = { AiringTodayTvShowsPagingSource(tvShowRemoteDataSource) }
  ).flow

  override fun searchTvShowsByTerm(searchTerm: String) = Pager(
    config = PagingConfig(pageSize = 20),
    pagingSourceFactory = { SearchTvShowsPagingSource(tvShowRemoteDataSource, searchTerm) }
  ).flow

  override fun getTvShowDetails(tvShowId: Int): Flow<Result<TvShowDetail>> = flow {
    try {
      val response = tvShowRemoteDataSource.getTvShowDetails(tvShowId)

      if (response.isSuccessful) {
        response.body()?.let {
          emit(Result.Success(it.toDomainTvShowDetail()))
        } ?: emit(Result.Error(DomainException("Error getting tv show details")))
      } else {
        response.errorBody()?.let { errorResponseBody ->
          val gson = Gson()
          val responseErrorBody =
            gson.fromJson(errorResponseBody.string(), ResponseErrorBody::class.java)

          emit(
            Result.Error(
              ApiException(
                responseErrorBody.statusCode,
                responseErrorBody.statusMessage
              )
            )
          )
        } ?: emit(Result.Error(DomainException("Unknown error")))
      }
    } catch (e: Exception) {
      emit(Result.Error(DomainException(e.message ?: "Something went wrong")))
    }
  }

  override fun getTvShowSeasonDetails(
    tvShowId: Int,
    seasonNumber: Int
  ): Flow<Result<SeasonDetail>> = flow {
    try {
      val response = tvShowRemoteDataSource.getTvShowSeasonDetails(tvShowId, seasonNumber)

      if (response.isSuccessful) {
        response.body()?.let {
          emit(Result.Success(it.toDomainSeasonDetail()))
        } ?: emit(Result.Error(DomainException("Error getting tv show season details")))
      } else {
        response.errorBody()?.let { errorResponseBody ->
          val gson = Gson()
          val responseErrorBody =
            gson.fromJson(errorResponseBody.string(), ResponseErrorBody::class.java)

          emit(
            Result.Error(
              ApiException(
                responseErrorBody.statusCode,
                responseErrorBody.statusMessage
              )
            )
          )
        } ?: emit(Result.Error(DomainException("Unknown error")))
      }
    } catch (e: Exception) {
      emit(Result.Error(DomainException(e.message ?: "Something went wrong")))
    }
  }

  override fun getLocalFavoriteTvShows(): Flow<List<FavoriteTvShow>> {
    return tvShowLocalDataSource.getAllFavoriteTvShows().map { favoriteTvShows ->
      favoriteTvShows.map { it.toDomainFavoriteTvShow() }
    }
  }

  override fun saveLocalFavoriteTvShow(favoriteTvShow: FavoriteTvShow): Flow<Result<Boolean>> = flow {
    try {
      tvShowLocalDataSource.saveFavoriteTvShow(favoriteTvShow.toDataFavoriteTvShow())
      emit(Result.Success(true))
    } catch (e: Exception) {
      emit(Result.Error(DomainException(e.message ?: "Something went wrong")))
    }
  }

  override fun removeLocalFavoriteTvShow(tvShowId: Int): Flow<Result<Boolean>> = flow {
    try {
      tvShowLocalDataSource.removeFavoriteTvShow(tvShowId)
      emit(Result.Success(true))
    } catch (e: Exception) {
      emit(Result.Error(DomainException(e.message ?: "Something went wrong")))
    }
  }

  override fun getLocalFavoriteShowById(tvShowId: Int): Flow<Result<FavoriteTvShow?>> = flow {
    try {
      val favoriteTvShow =
        tvShowLocalDataSource.getLocalFavoriteShowById(tvShowId)?.toDomainFavoriteTvShow()
      emit(Result.Success(favoriteTvShow))
    } catch (e: Exception) {
      emit(Result.Error(DomainException(e.message ?: "Something went wrong")))
    }
  }
}
