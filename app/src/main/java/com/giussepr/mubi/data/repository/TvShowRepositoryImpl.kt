/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.repository

import com.giussepr.mubi.data.repository.datasource.TvShowRemoteDataSource
import com.giussepr.mubi.domain.model.Result
import com.giussepr.mubi.domain.repository.TvShowRepository
import javax.inject.Inject
import com.giussepr.mubi.domain.model.Result.*
import com.giussepr.mubi.domain.model.TvShow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TvShowRepositoryImpl @Inject constructor(private val tvShowRemoteDataSource: TvShowRemoteDataSource) :
  TvShowRepository {

  override fun getTopRatedTvShows(): Flow<Result<List<TvShow>>> = flow {
    val response = tvShowRemoteDataSource.getTopRatedTvShows()

    if (response.isSuccessful) {
      response.body()?.let { result ->
        emit(Success(result.results.map { it.toDomainTvShow() }))
      } ?: emit(Error(response.message()))
    } else {
      emit(Error(response.message()))
    }
  }
}
