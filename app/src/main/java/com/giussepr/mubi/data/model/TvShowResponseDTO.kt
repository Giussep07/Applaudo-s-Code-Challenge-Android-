/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.model

import com.google.gson.annotations.SerializedName

data class TvShowResponseDTO(
  val page: Int,
  val results: List<TvShowDTO>,
  @SerializedName("total_pages")
  val totalPages: Int,
  @SerializedName("total_results")
  val totalResults: Int,
)
