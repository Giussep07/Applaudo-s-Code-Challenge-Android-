/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.screens.tvshowdetail.model

data class UiTvShowDetail(
  val id: Int,
  val imageUrl: String,
  val originalName: String,
  val name: String,
  val averageRate: Double,
  val overview: String,
)
