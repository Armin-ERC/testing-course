package com.aerc.cursotestingandroid.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.aerc.cursotestingandroid.core.data.DefaultDispatchersProvider
import com.aerc.cursotestingandroid.core.domain.coroutines.DispatchersProvider
import com.aerc.cursotestingandroid.productlist.data.local.database.MiniMarketDatabase
import com.aerc.cursotestingandroid.productlist.data.local.database.dao.ProductDao
import com.aerc.cursotestingandroid.productlist.data.local.database.dao.PromotionDao
import com.aerc.cursotestingandroid.productlist.data.repository.ProductRepositoryImpl
import com.aerc.cursotestingandroid.productlist.data.repository.PromotionRepositoryImpl
import com.aerc.cursotestingandroid.productlist.data.repository.SettingsRepositoryImpl
import com.aerc.cursotestingandroid.productlist.domain.repository.ProductRepository
import com.aerc.cursotestingandroid.productlist.domain.repository.PromotionRepository
import com.aerc.cursotestingandroid.productlist.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
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

    @Provides
    @Singleton
    fun providePromotionRepository(promotionRepositoryImpl: PromotionRepositoryImpl): PromotionRepository {
        return promotionRepositoryImpl
    }

    @Provides
    fun providesPromotionDao(database: MiniMarketDatabase): PromotionDao {
        return database.promotionDao()
    }

    @Provides
    fun providesProductDao(database: MiniMarketDatabase): ProductDao {
        return database.productDao()
    }

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): MiniMarketDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = MiniMarketDatabase::class.java,
            name = "minimarket_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences>{
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl) : SettingsRepository{
        return settingsRepositoryImpl
    }

}
