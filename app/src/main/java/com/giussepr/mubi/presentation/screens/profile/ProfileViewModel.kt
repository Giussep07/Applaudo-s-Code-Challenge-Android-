/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.giussepr.mubi.domain.model.FavoriteTvShow
import com.giussepr.mubi.domain.model.fold
import com.giussepr.mubi.domain.usecase.GetLocalFavoriteTvShowsUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val getLocalFavoriteTvShowsUseCase: GetLocalFavoriteTvShowsUseCase) :
  ViewModel() {

  private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
  val uiState: StateFlow<ProfileUiState> = _uiState

  private val _navigateToTvShowDetails = MutableStateFlow("")
  val navigateToTvShowDetails: StateFlow<String> = _navigateToTvShowDetails

  fun getFavoriteTvShows() {
    getLocalFavoriteTvShowsUseCase().map { result ->
      result.fold(
        onSuccess = { favoriteTvShows ->
          _uiState.value = ProfileUiState.Success(favoriteTvShows)
        },
        onFailure = { error ->
          _uiState.value = ProfileUiState.Error(error.message)
        }
      )
    }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
  }

  fun onTvShowItemClicked(favoriteTvShow: FavoriteTvShow) {
    // Map Tv Show to UiTvShowDetail json string
    val gson = Gson()
    val uiTvShowDetailJson = gson.toJson(favoriteTvShow.toUiTvShowDetail())
    _navigateToTvShowDetails.value = uiTvShowDetailJson
  }

  fun navigateToTvShowDetailsHandled() {
    _navigateToTvShowDetails.value = ""
  }

  sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val favoriteTvShows: List<FavoriteTvShow>) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
  }
}
