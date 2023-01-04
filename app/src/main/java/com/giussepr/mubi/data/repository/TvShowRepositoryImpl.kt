/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.giussepr.mubi.data.paging.TvShowPagingSource
import com.giussepr.mubi.data.repository.datasource.TvShowRemoteDataSource
import com.giussepr.mubi.domain.repository.TvShowRepository
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(private val tvShowRemoteDataSource: TvShowRemoteDataSource) :
  TvShowRepository {

  override fun getTopRatedTvShows() = Pager(
    config = PagingConfig(pageSize = 20),
    pagingSourceFactory = { TvShowPagingSource(tvShowRemoteDataSource) }
  ).flow
}
