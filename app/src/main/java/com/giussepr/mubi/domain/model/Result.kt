/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.domain.model

import com.giussepr.mubi.domain.error.DomainException

sealed class Result<T> {
  data class Success<T>(val data: T) : Result<T>()
  data class Error<T>(val domainException: DomainException) : Result<T>()
}

inline fun <R, T> Result<T>.fold(
  onSuccess: (value: T) -> R,
  onFailure: (domainException: DomainException) -> R
): R = when (this) {
  is Result.Success -> onSuccess(data)
  is Result.Error -> onFailure(domainException)
}
