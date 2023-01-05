/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.domain.error

import java.io.IOException

open class DomainException(override val message: String = "") : Throwable(message)
object NoTvShowsResultsException : DomainException()
class NoInternetConnection : IOException("No internet connection")
data class ApiException(val code: Int, override val message: String) : DomainException()
