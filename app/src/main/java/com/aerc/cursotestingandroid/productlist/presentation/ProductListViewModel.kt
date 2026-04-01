package com.aerc.cursotestingandroid.productlist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aerc.cursotestingandroid.productlist.domain.model.ProductWithPromotion
import com.aerc.cursotestingandroid.productlist.domain.model.SortOption
import com.aerc.cursotestingandroid.productlist.domain.usecase.GetProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProducts: GetProducts
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductListUiState>(ProductListUiState.Loading)
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<ProductListEvent>(extraBufferCapacity = 1)
    val events: SharedFlow<ProductListEvent> = _events

    private val _filtersVisible = MutableStateFlow<Boolean>(true)
    val filtersVisible: StateFlow<Boolean> = _filtersVisible.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        _uiState.value = ProductListUiState.Loading
        getProducts()
            .onEach { products : List<ProductWithPromotion>->
                val categories = products.map { it.product.category }.distinct().sorted()
                _uiState.value =
                    ProductListUiState.Success(
                        products = products,
                        categories = categories,
                        selectedCategory = null,
                        sortOption = SortOption.NONE
                    )
            }
            .catch { e: Throwable ->
                _uiState.value = ProductListUiState.Error(e.message.orEmpty())
            }
            .launchIn(viewModelScope)
    }

    fun setCategory(category: String?) {
        viewModelScope.launch {
            //llamar settingRepository
        }
    }

    fun setOrder(sortOption: SortOption) {

    }

    fun setFiltersVisible(showFilters: Boolean) {
        _filtersVisible.value = showFilters
    }
}
