package com.aerc.cursotestingandroid.di

import com.aerc.cursotestingandroid.core.data.DefaultDispatchersProvider
import com.aerc.cursotestingandroid.core.domain.coroutines.DispatchersProvider
import com.aerc.cursotestingandroid.productlist.data.repository.ProductRepositoryImpl
import com.aerc.cursotestingandroid.productlist.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDispatchersProvider(defaultDispatchersProvider: DefaultDispatchersProvider): DispatchersProvider {
        return defaultDispatchersProvider
    }

    @Provides
    @Singleton
    fun provideProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository {
        return productRepositoryImpl
    }

}
