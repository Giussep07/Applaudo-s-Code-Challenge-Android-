/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.core.di

import com.giussepr.mubi.data.api.TmdbApi
import com.giussepr.mubi.data.database.dao.FavoriteTvShowDao
import com.giussepr.mubi.data.repository.datasource.TvShowRemoteDataSource
import com.giussepr.mubi.data.repository.datasource.TvShowRemoteDataSourceImpl
import com.giussepr.mubi.data.repository.datasource.local.TvShowLocalDataSource
import com.giussepr.mubi.data.repository.datasource.local.TvShowLocalDataSourceImpl
import com.giussepr.mubi.presentation.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
object DataSourceModule {

  @Provides
  fun provideTvShowRemoteDataSource(
    tmdbApi: TmdbApi,
    @Named(Constants.API_KEY) apiKey: String
  ): TvShowRemoteDataSource {
    return TvShowRemoteDataSourceImpl(tmdbApi, apiKey)
  }

  @Provides
  fun provideTvShowLocalDataSource(favoriteTvShowDao: FavoriteTvShowDao): TvShowLocalDataSource {
    return TvShowLocalDataSourceImpl(favoriteTvShowDao)
  }
}
