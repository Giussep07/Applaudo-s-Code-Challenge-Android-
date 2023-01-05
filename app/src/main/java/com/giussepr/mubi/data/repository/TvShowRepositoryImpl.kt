/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.giussepr.mubi.data.database.MubiDatabase
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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(
  private val tvShowRemoteDataSource: TvShowRemoteDataSource,
  private val tvShowLocalDataSource: TvShowLocalDataSource,
  private val mubiDatabase: MubiDatabase
) : TvShowRepository {

  @OptIn(ExperimentalPagingApi::class)
  override fun getTopRatedTvShows() = Pager(
    config = PagingConfig(pageSize = 20),
    remoteMediator = TopRatedTvShowRemoteMediator(
      tvShowRemoteDataSource,
      tvShowLocalDataSource,
      mubiDatabase
    ),
    pagingSourceFactory = { tvShowLocalDataSource.getTopRatedTvShows() }
  ).flow.map { pagingData ->
    pagingData.map {
      it.toDomainTvShow()
    }
  }

  @OptIn(ExperimentalPagingApi::class)
  override fun getPopularTvShows() = Pager(
    config = PagingConfig(pageSize = 20),
    remoteMediator = PopularTvShowRemoteMediator(
      tvShowRemoteDataSource,
      tvShowLocalDataSource,
      mubiDatabase
    ),
    pagingSourceFactory = { tvShowLocalDataSource.getPopularTvShows() }
  ).flow.map { pagingData ->
    pagingData.map {
      it.toDomainTvShow()
    }
  }

  @OptIn(ExperimentalPagingApi::class)
  override fun getOnTvShows() = Pager(
    config = PagingConfig(pageSize = 20),
    remoteMediator = OnTvShowRemoteMediator(
      tvShowRemoteDataSource,
      tvShowLocalDataSource,
      mubiDatabase
    ),
    pagingSourceFactory = { tvShowLocalDataSource.getOnTvShows() }
  ).flow.map { pagingData ->
    pagingData.map {
      it.toDomainTvShow()
    }
  }

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

  override fun getLocalFavoriteTvShows(): Flow<Result<List<FavoriteTvShow>>> = flow {
    try {
      tvShowLocalDataSource.getAllFavoriteTvShows().map { favoriteTvShows ->
        emit(Result.Success(favoriteTvShows.map { it.toDomainFavoriteTvShow() }))
      }.collect()
    } catch (e: Exception) {
      emit(Result.Error(DomainException(e.message ?: "Something went wrong")))
    }
  }

  override fun saveLocalFavoriteTvShow(favoriteTvShow: FavoriteTvShow): Flow<Result<Boolean>> =
    flow {
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
