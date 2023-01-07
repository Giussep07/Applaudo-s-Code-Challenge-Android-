/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.giussepr.mubi.domain.model.TvShow
import com.giussepr.mubi.domain.usecase.SearchTvShowsByTextUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchTvShowsByTextUseCase: SearchTvShowsByTextUseCase) :
  ViewModel() {

  private val _tvShowList: MutableStateFlow<Flow<PagingData<TvShow>>?> =
    MutableStateFlow(null)
  val tvShowList: StateFlow<Flow<PagingData<TvShow>>?> = _tvShowList

  private val _navigateToTvShowDetails = MutableStateFlow("")
  val navigateToTvShowDetails: StateFlow<String> = _navigateToTvShowDetails

  fun searchTvShowsByTerm(searchTerm: String) {
    _tvShowList.value = searchTvShowsByTextUseCase(searchTerm).cachedIn(viewModelScope)
  }

  fun onTvShowItemClicked(tvShow: TvShow) {
    // Map Tv Show to UiTvShowDetail json string
    val gson = Gson()
    val uiTvShowDetailJson = gson.toJson(tvShow.toUiTvShowDetail())
    _navigateToTvShowDetails.value = uiTvShowDetailJson
  }

  fun navigateToTvShowDetailsHandled() {
    _navigateToTvShowDetails.value = ""
  }
}
