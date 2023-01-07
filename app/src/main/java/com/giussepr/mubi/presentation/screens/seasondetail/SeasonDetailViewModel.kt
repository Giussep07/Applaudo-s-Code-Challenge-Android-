/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.screens.seasondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giussepr.mubi.domain.model.SeasonDetail
import com.giussepr.mubi.domain.model.fold
import com.giussepr.mubi.domain.usecase.GetTvShowSeasonDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SeasonDetailViewModel @Inject constructor(
  private val getTvShowSeasonDetailsUseCase: GetTvShowSeasonDetailsUseCase,
  savedStateHandle: SavedStateHandle,
) : ViewModel() {

  private var tvShowId: Int = -1
  private var seasonNumber: Int = -1

  private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
  val uiState: MutableStateFlow<UiState> = _uiState

  init {
    savedStateHandle.get<Int>("tvShowId")?.let { tvShowId ->
      this.tvShowId = tvShowId
    }
    savedStateHandle.get<Int>("seasonNumber")?.let { seasonNumber ->
      this.seasonNumber = seasonNumber
    }

    getSeasonDetail()
  }

  private fun getSeasonDetail() {
    getTvShowSeasonDetailsUseCase.invoke(tvShowId, seasonNumber).map { result ->
      result.fold(
        onSuccess = { _uiState.value = UiState.Success(it) },
        onFailure = { _uiState.value = UiState.Error(it.message) }
      )
    }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
  }

  sealed class UiState {
    object Loading : UiState()
    data class Success(val seasonDetail: SeasonDetail) : UiState()
    data class Error(val message: String) : UiState()
  }
}
