package com.aerc.cursotestingandroid.settings.presentation

import com.aerc.cursotestingandroid.core.domain.model.ThemeMode

data class SettingsUiState(
    val inStockOnly: Boolean = false,
    val themeMode: ThemeMode = ThemeMode.System
)
