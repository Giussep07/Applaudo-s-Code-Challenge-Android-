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
import com.giussepr.mubi.domain.usecase.GetPopularTvShowsUseCase
import com.giussepr.mubi.domain.usecase.GetTopRatedTvShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val getTopRatedTvShowsUseCase: GetTopRatedTvShowsUseCase,
  private val getPopularTvShowsUseCase: GetPopularTvShowsUseCase,
) : ViewModel() {

  private val _tvShowFilter: MutableStateFlow<TvShowFilter> =
    MutableStateFlow(TvShowFilter.TOP_RATED)
  val tvShowFilter: StateFlow<TvShowFilter> = _tvShowFilter

  private val _tvShowList: MutableStateFlow<Flow<PagingData<TvShow>>> =
    MutableStateFlow(getTopRatedTvShowsUseCase.invoke().cachedIn(viewModelScope))
  val tvShowList: StateFlow<Flow<PagingData<TvShow>>> = _tvShowList

  fun changeTvShowFilter(tvShowFilter: TvShowFilter) {
    _tvShowFilter.value = tvShowFilter

    when (tvShowFilter) {
      TvShowFilter.TOP_RATED -> _tvShowList.value =
        getTopRatedTvShowsUseCase.invoke().cachedIn(viewModelScope)
      TvShowFilter.POPULAR -> _tvShowList.value =
        getPopularTvShowsUseCase.invoke().cachedIn(viewModelScope)
      else -> {}
    }
  }
}


