/*
 * Created by Giussep Ricardo on 03/01/2023
 * Copyright (c) 2023 . All rights reserved.
 */

package com.giussepr.mubi.presentation.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun Double.formatToRating(): String {
    val decimalFormat = DecimalFormat("#.0")
    decimalFormat.roundingMode = java.math.RoundingMode.DOWN
    decimalFormat.decimalFormatSymbols = DecimalFormatSymbols().apply {
        decimalSeparator = '.'
    }
    return decimalFormat.format(this)
}
