/*
 * Created by Giussep Ricardo on 07/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.core.di

import com.giussepr.mubi.domain.usecase.CheckIfTvShowIsFavoriteUseCase
import com.giussepr.mubi.domain.usecase.GetTvShowDetailsUseCase
import com.giussepr.mubi.domain.usecase.RemoveLocalFavoriteTvShowUseCase
import com.giussepr.mubi.domain.usecase.SaveLocalFavoriteTvShowUseCase
import com.giussepr.mubi.presentation.screens.tvshowdetail.TvShowDetailViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.MutableStateFlow

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

  @Provides
  fun provideTvShowDetailViewModel(
    getTvShowDetailsUseCase: GetTvShowDetailsUseCase,
    saveLocalFavoriteTvShowsUseCase: SaveLocalFavoriteTvShowUseCase,
    removeLocalFavoriteTvShowUseCase: RemoveLocalFavoriteTvShowUseCase,
    checkIfTvShowIsFavoriteUseCase: CheckIfTvShowIsFavoriteUseCase,
  ) = TvShowDetailViewModel(
    getTvShowDetailsUseCase = getTvShowDetailsUseCase,
    saveLocalFavoriteTvShowsUseCase = saveLocalFavoriteTvShowsUseCase,
    removeLocalFavoriteTvShowUseCase = removeLocalFavoriteTvShowUseCase,
    checkIfTvShowIsFavoriteUseCase = checkIfTvShowIsFavoriteUseCase,
    _isFavorite = MutableStateFlow(false),
  )

}
