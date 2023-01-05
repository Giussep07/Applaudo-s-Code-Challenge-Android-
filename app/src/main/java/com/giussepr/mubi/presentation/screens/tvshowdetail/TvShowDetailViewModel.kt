/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.screens.tvshowdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giussepr.mubi.domain.model.TvShowDetail
import com.giussepr.mubi.domain.model.fold
import com.giussepr.mubi.domain.usecase.*
import com.giussepr.mubi.presentation.screens.tvshowdetail.model.UiTvShowDetail
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(
  savedStateHandle: SavedStateHandle,
  private val getTvShowDetailsUseCase: GetTvShowDetailsUseCase,
  private val saveLocalFavoriteTvShowsUseCase: SaveLocalFavoriteTvShowUseCase,
  private val removeLocalFavoriteTvShowUseCase: RemoveLocalFavoriteTvShowUseCase,
  private val checkIfTvShowIsFavoriteUseCase: CheckIfTvShowIsFavoriteUseCase
) : ViewModel() {

  private var tvShowId: Int = -1

  private val _uiTvShowDetail: MutableStateFlow<UiTvShowDetail?> = MutableStateFlow(null)
  val uiTvShowDetail: MutableStateFlow<UiTvShowDetail?> = _uiTvShowDetail

  private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
  val uiState: MutableStateFlow<UiState> = _uiState

  private val _isFavorite: MutableStateFlow<Boolean> = MutableStateFlow(false)
  val isFavorite: MutableStateFlow<Boolean> = _isFavorite

  init {
    savedStateHandle.get<String>("tvShowDetailJson")?.let { tvShowDetailJson ->
      val gson = Gson()
      val uiTvShowDetail = gson.fromJson(tvShowDetailJson, UiTvShowDetail::class.java)
      this.tvShowId = uiTvShowDetail.id
      _uiTvShowDetail.value = uiTvShowDetail
    }

    getTvShowDetail()
    checkIfTvShowIsFavorite()
  }

  private fun getTvShowDetail() {
    getTvShowDetailsUseCase.invoke(tvShowId).map { result ->
      result.fold(
        onSuccess = { _uiState.value = UiState.Success(it) },
        onFailure = { _uiState.value = UiState.Error(it.message) }
      )
    }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
  }

  private fun checkIfTvShowIsFavorite() {
    checkIfTvShowIsFavoriteUseCase.invoke(tvShowId).map { result ->
      result.fold(
        onSuccess = { _isFavorite.value = it },
        onFailure = {
          // Should not happen
        }
      )
    }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
  }

  fun onFavoriteButtonClicked(uiTvShowDetail: UiTvShowDetail) {
    if (_isFavorite.value) {
      removeLocalFavoriteTvShow(uiTvShowDetail)
    } else {
      saveLocalFavoriteTvShow(uiTvShowDetail)
    }
  }

  private fun saveLocalFavoriteTvShow(uiTvShowDetail: UiTvShowDetail) {
    saveLocalFavoriteTvShowsUseCase(uiTvShowDetail.toDomainFavoriteTvShow()).map { result ->
      result.fold(
        onSuccess = { _isFavorite.value = it },
        onFailure = {
          // Should not happen
        }
      )
    }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
  }

  private fun removeLocalFavoriteTvShow(uiTvShowDetail: UiTvShowDetail) {
    removeLocalFavoriteTvShowUseCase(uiTvShowDetail.id).map { result ->
      result.fold(
        onSuccess = { _isFavorite.value = !_isFavorite.value },
        onFailure = {
          // Should not happen
        }
      )
    }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
  }

  sealed class UiState {
    object Loading : UiState()
    data class Success(val tvShowDetail: TvShowDetail) : UiState()
    data class Error(val message: String) : UiState()
  }
}
