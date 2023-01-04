/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.core.di

import com.giussepr.mubi.domain.repository.TvShowRepository
import com.giussepr.mubi.domain.usecase.GetPopularTvShowsUseCase
import com.giussepr.mubi.domain.usecase.GetTopRatedTvShowsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

  @Provides
  fun provideGetTopRatedTvShowsUseCase(tvShowRepository: TvShowRepository) =
    GetTopRatedTvShowsUseCase(tvShowRepository)

  @Provides
  fun provideGetPopularTvShowsUseCase(tvShowRepository: TvShowRepository) =
    GetPopularTvShowsUseCase(tvShowRepository)
}
