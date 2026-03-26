package com.aerc.cursotestingandroid.productlist.data.repository

import com.aerc.cursotestingandroid.core.domain.coroutines.DispatchersProvider
import com.aerc.cursotestingandroid.productlist.data.remote.RemoteDataSource
import com.aerc.cursotestingandroid.productlist.domain.model.Product
import com.aerc.cursotestingandroid.productlist.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    val remoteDataSource: RemoteDataSource,
    val dispatchers: DispatchersProvider
) : ProductRepository {

    override fun getProducts(): Flow<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun getProductById(id: String): Flow<Product?> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshProduct() {
        withContext(dispatchers.io){
            remoteDataSource.getProducts()
        }
    }

}
