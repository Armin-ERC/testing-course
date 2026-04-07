package com.aerc.cursotestingandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aerc.cursotestingandroid.core.domain.model.ThemeMode
import com.aerc.cursotestingandroid.productlist.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    val themeMode: Flow<ThemeMode> = settingsRepository.themeMode
        .stateIn(
            scope = viewModelScope,
            initialValue = ThemeMode.System,
            started = SharingStarted.WhileSubscribed(5000)
        )

}
