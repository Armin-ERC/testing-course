package com.aerc.cursotestingandroid.productlist.domain.usecase

import com.aerc.cursotestingandroid.productlist.domain.model.Product
import com.aerc.cursotestingandroid.productlist.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProducts @Inject constructor(
    private val productRepository: ProductRepository
){
    operator fun invoke(): Flow<List<Product>> {
        return productRepository.getProducts()
    }
}
