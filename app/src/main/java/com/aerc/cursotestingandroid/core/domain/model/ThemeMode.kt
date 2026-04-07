package com.aerc.cursotestingandroid.core.domain.model

sealed class ThemeMode(val id: Int) {
    data object System: ThemeMode(0)
    data object Light: ThemeMode(1)
    data object Dark: ThemeMode(2)
}
