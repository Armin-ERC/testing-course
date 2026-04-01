package com.aerc.cursotestingandroid.productlist.presentation

import com.aerc.cursotestingandroid.productlist.domain.model.Product
import com.aerc.cursotestingandroid.productlist.domain.model.ProductWithPromotion
import com.aerc.cursotestingandroid.productlist.domain.model.SortOption

sealed class ProductListUiState {
    data object Loading: ProductListUiState()
    data class Error(val message: String) : ProductListUiState()
    data class Success(
        val products: List<ProductWithPromotion>,
        val categories: List<String>,
        val selectedCategory: String?,
        val sortOption: SortOption
    ): ProductListUiState()
}
