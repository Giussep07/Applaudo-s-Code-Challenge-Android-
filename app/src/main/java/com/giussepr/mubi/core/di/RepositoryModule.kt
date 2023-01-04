/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.core.di

import com.giussepr.mubi.data.repository.TvShowRepositoryImpl
import com.giussepr.mubi.data.repository.datasource.TvShowRemoteDataSource
import com.giussepr.mubi.domain.repository.TvShowRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

  @Provides
  fun provideTvShowRepository(tvShowRemoteDataSource: TvShowRemoteDataSource): TvShowRepository {
    return TvShowRepositoryImpl(tvShowRemoteDataSource)
  }
}
