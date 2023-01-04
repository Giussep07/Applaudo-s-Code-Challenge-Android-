/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.giussepr.mubi.domain.model.TvShow
import com.giussepr.mubi.domain.usecase.GetTopRatedTvShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val getTopRatedTvShowsUseCase: GetTopRatedTvShowsUseCase
) : ViewModel() {

  private val _tvShowFilter: MutableStateFlow<TvShowFilter> =
    MutableStateFlow(TvShowFilter.TOP_RATED)
  val tvShowFilter: StateFlow<TvShowFilter> = _tvShowFilter

  fun changeTvShowFilter(tvShowFilter: TvShowFilter) {
    _tvShowFilter.value = tvShowFilter
  }

  fun getTopRatedMovies(): Flow<PagingData<TvShow>> {
    return getTopRatedTvShowsUseCase().cachedIn(viewModelScope)
  }
}


