package com.aerc.cursotestingandroid.productlist.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.aerc.cursotestingandroid.core.domain.model.ThemeMode
import com.aerc.cursotestingandroid.productlist.domain.model.SortOption
import com.aerc.cursotestingandroid.productlist.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    companion object {
        private val IN_STOCK_ONLY_KEY = booleanPreferencesKey("in_stock_only_key")
        private val THEME_MODE_KEY = intPreferencesKey("theme_mode")
        private val SELECTED_CATEGORY_KEY = stringPreferencesKey("selected_category_key")
        private val FILTERS_VISIBLE_KEY = booleanPreferencesKey("filters_visible_key")
        private val SORT_OPTION_KEY = stringPreferencesKey("sort_option_key")
    }

    private val dataStoreFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }

    override val inStockOnly: Flow<Boolean> = dataStoreFlow.map { preferences ->
        preferences[IN_STOCK_ONLY_KEY] ?: false
    }
    override val themeMode: Flow<ThemeMode> = dataStoreFlow.map { preferences ->
        when (preferences[THEME_MODE_KEY]) {
            ThemeMode.System.id -> ThemeMode.System
            ThemeMode.Light.id -> ThemeMode.Light
            ThemeMode.Dark.id -> ThemeMode.Dark
            else -> ThemeMode.System
        }
    }
    override val selectedCategory: Flow<String?> = dataStoreFlow.map { preferences ->
        preferences[SELECTED_CATEGORY_KEY]
    }
    override val filtersVisible: Flow<Boolean> = dataStoreFlow.map { preferences ->
        preferences[FILTERS_VISIBLE_KEY] ?: true
    }
    override val sortOption: Flow<SortOption> = dataStoreFlow.map { preferences ->
        val raw = preferences[SORT_OPTION_KEY]
        runCatching {
            SortOption.valueOf(
                raw ?: SortOption.NONE.name
            )
        }.getOrDefault(SortOption.NONE)
    }

    override suspend fun setInStockOnly(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[IN_STOCK_ONLY_KEY] = value
        }
    }

    override suspend fun setThemeMode(value: ThemeMode) {
        dataStore.edit { preferences ->
            when (value) {
                ThemeMode.Dark -> preferences[THEME_MODE_KEY] = ThemeMode.Dark.id
                ThemeMode.Light -> preferences[THEME_MODE_KEY] = ThemeMode.Light.id
                ThemeMode.System -> preferences[THEME_MODE_KEY] = ThemeMode.System.id
            }
        }
    }

    override suspend fun setSelectedCategory(value: String?) {
        dataStore.edit { preferences ->
            if (value == null) {
                preferences.remove(SELECTED_CATEGORY_KEY)
            } else {
                preferences[SELECTED_CATEGORY_KEY] = value
            }
        }
    }

    override suspend fun setFiltersVisible(value: Boolean) {
        dataStore.edit { preferences ->
            preferences[FILTERS_VISIBLE_KEY] = value
        }
    }

    override suspend fun setSortOption(value: SortOption) {
        dataStore.edit { preferences ->
            preferences[SORT_OPTION_KEY] = value.name
        }
    }
}
