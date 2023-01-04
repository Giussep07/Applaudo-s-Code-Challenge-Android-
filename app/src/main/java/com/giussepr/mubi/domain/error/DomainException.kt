/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.domain.error

open class DomainException(override val message: String = "") : Throwable(message)
data class ApiException(val code: Int, override val message: String) : DomainException()
