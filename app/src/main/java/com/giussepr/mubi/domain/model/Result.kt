/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.domain.model

sealed class Result<T> {
  data class Success<T>(val data: T) : Result<T>()
  data class Error<T>(val message: String) : Result<T>()
}
