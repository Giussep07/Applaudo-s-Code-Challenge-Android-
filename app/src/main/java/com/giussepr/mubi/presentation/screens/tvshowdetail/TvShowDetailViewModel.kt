/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.screens.tvshowdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.giussepr.mubi.presentation.screens.tvshowdetail.model.UiTvShowDetail
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class TvShowDetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {

  private val _uiTvShowDetail: MutableStateFlow<UiTvShowDetail?> = MutableStateFlow(null)
  val uiTvShowDetail: MutableStateFlow<UiTvShowDetail?> = _uiTvShowDetail

  init {
    savedStateHandle.get<String>("tvShowDetailJson")?.let { tvShowDetailJson ->
      val gson = Gson()
      _uiTvShowDetail.value = gson.fromJson(tvShowDetailJson, UiTvShowDetail::class.java)
    }
  }
}
