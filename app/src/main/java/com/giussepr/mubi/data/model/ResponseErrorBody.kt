/*
 * Created by Giussep Ricardo on 04/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.data.model

import com.google.gson.annotations.SerializedName

data class ResponseErrorBody(
  @SerializedName("status_code")
  val statusCode: Int,
  @SerializedName("status_message")
  val statusMessage: String
)
