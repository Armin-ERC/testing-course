package com.aerc.cursotestingandroid.productlist.data.repository

import com.aerc.cursotestingandroid.core.domain.coroutines.DispatchersProvider
import com.aerc.cursotestingandroid.productlist.data.local.LocalDataSource
import com.aerc.cursotestingandroid.productlist.data.local.database.entity.PromotionEntity
import com.aerc.cursotestingandroid.productlist.data.mapers.toDomain
import com.aerc.cursotestingandroid.productlist.data.mapers.toEntity
import com.aerc.cursotestingandroid.productlist.data.remote.RemoteDataSource
import com.aerc.cursotestingandroid.productlist.domain.model.Promotion
import com.aerc.cursotestingandroid.productlist.domain.repository.PromotionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PromotionRepositoryImpl @Inject constructor(
    val remoteDataSource: RemoteDataSource,
    val localDataSource: LocalDataSource,
    val dispatchers: DispatchersProvider,
    val json: Json
) : PromotionRepository {

    private val refreshScope = CoroutineScope(SupervisorJob() + dispatchers.io)
    private val refreshMutex = Mutex()

    override fun getActivePromotions(): Flow<List<Promotion>> {
        return localDataSource.getAllPromotions()
            .map { entities -> entities.mapNotNull { it.toDomain(json) } }
            .onStart {
                refreshScope.launch {
                    if (!refreshMutex.tryLock()) return@launch

                    try {
                        refreshPromotions()
                    } catch (e: Exception) {

                    } finally {
                        refreshMutex.unlock()
                    }
                }
            }
            .catch {
                //Log important
            }
    }

    override suspend fun refreshPromotions() {
        withContext(dispatchers.io) {
            val promotions = remoteDataSource.getPromotions().getOrThrow()
            val promotionsEntity: List<PromotionEntity> =
                promotions.mapNotNull { it.toEntity(json) }
            localDataSource.savePromotions(promotionsEntity)
        }
    }
}
