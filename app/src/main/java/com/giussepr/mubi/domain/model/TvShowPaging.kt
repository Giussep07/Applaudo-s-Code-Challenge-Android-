/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.domain.model

data class TvShowPaging(
  val page: Int,
  val results: List<TvShow>,
  val totalPages: Int,
  val totalResults: Int,
)
