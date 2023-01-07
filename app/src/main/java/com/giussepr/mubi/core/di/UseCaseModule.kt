/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.core.di

import com.giussepr.mubi.domain.repository.TvShowRepository
import com.giussepr.mubi.domain.usecase.*
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

  @Provides
  fun provideGetOnTvShowsUseCase(tvShowRepository: TvShowRepository) =
    GetOnTvTvShowsUseCase(tvShowRepository)

  @Provides
  fun provideGetAiringTodayTvShowsUseCase(tvShowRepository: TvShowRepository) =
    GetAiringTodayTvShowsUseCase(tvShowRepository)

  @Provides
  fun provideSearchTvShowsByTextUseCase(tvShowRepository: TvShowRepository) =
    SearchTvShowsByTextUseCase(tvShowRepository)

  @Provides
  fun provideGetTvShowDetailsUseCase(tvShowRepository: TvShowRepository) =
    GetTvShowDetailsUseCase(tvShowRepository)

  @Provides
  fun provideGetTvShowSeasonDetailsUseCase(tvShowRepository: TvShowRepository) =
    GetTvShowSeasonDetailsUseCase(tvShowRepository)

  @Provides
  fun provideGetLocalFavoriteTvShowsUseCase(tvShowRepository: TvShowRepository) =
    GetLocalFavoriteTvShowsUseCase(tvShowRepository)

  @Provides
  fun provideCheckIfTvShowIsFavoriteUseCase(tvShowRepository: TvShowRepository) =
    CheckIfTvShowIsFavoriteUseCase(tvShowRepository)

  @Provides
  fun provideRemoveLocalFavoriteTvShowUseCase(tvShowRepository: TvShowRepository) =
    RemoveLocalFavoriteTvShowUseCase(tvShowRepository)

  @Provides
  fun provideSaveLocalFavoriteTvShowUseCase(tvShowRepository: TvShowRepository) =
    SaveLocalFavoriteTvShowUseCase(tvShowRepository)
}
