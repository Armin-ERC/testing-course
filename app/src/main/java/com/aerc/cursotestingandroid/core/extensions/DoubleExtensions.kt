package com.aerc.cursotestingandroid.core.extensions

import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToInt

fun Double.toLocalPriceAndSimbol(): String {
    val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault())
    return formatter.format(this)
}

fun Double.round2Decimals(): Double {
    return (this * 100).roundToInt() / 100.0
}
