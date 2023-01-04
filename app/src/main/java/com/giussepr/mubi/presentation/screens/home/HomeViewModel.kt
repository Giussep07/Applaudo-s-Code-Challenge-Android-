/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giussepr.mubi.domain.model.Result
import com.giussepr.mubi.domain.model.TvShow
import com.giussepr.mubi.domain.usecase.GetTopRatedTvShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val getTopRatedTvShowsUseCase: GetTopRatedTvShowsUseCase
) : ViewModel() {

  private val _homeState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.Loading)
  val homeState: StateFlow<HomeState>
    get() = _homeState

  /*fun getTopRatedMovies() = flow<Result<List<TvShow>>> {
    when (val response = tvShowRepository.getTopRatedTvShows()) {
      is Result.Success -> _homeState.value = HomeState.Success(response.data)
      is Result.Error -> _homeState.value = HomeState.Error(response.message)
    }
  }.onStart {
    _homeState.value = HomeState.Loading
  }.flowOn(Dispatchers.IO).launchIn(viewModelScope)*/

  fun getTopRatedMovies() {
    viewModelScope.launch {
      getTopRatedTvShowsUseCase()
        .flowOn(Dispatchers.IO)
        .onStart { _homeState.value = HomeState.Loading }
        .collect {
          when (it) {
            is Result.Success -> _homeState.value = HomeState.Success(it.data)
            is Result.Error -> _homeState.value = HomeState.Error(it.message)
          }
        }
    }
  }

  sealed class HomeState {
    object Loading : HomeState()
    data class Success(val tvShowList: List<TvShow>) : HomeState()
    data class Error(val message: String) : HomeState()
  }
}


