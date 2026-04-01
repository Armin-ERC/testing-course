package com.aerc.cursotestingandroid.productlist.data.mapers

import com.aerc.cursotestingandroid.productlist.data.local.database.entity.PromotionEntity
import com.aerc.cursotestingandroid.productlist.data.remote.response.PromotionResponse
import com.aerc.cursotestingandroid.productlist.domain.model.Promotion
import com.aerc.cursotestingandroid.productlist.domain.model.PromotionType
import com.aerc.cursotestingandroid.productlist.domain.model.PromotionType.*
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import java.time.Instant

fun PromotionResponse.toEntity(json: Json): PromotionEntity? {

    if (startAtEpoch == null || endAtEpoch == null) return null

    val productsId: List<String> = listOf(productId)
    val productIdJson = json.encodeToString(
        serializer = ListSerializer(String.serializer()),
        value = productsId
    )

    return PromotionEntity(
        id = id,
        productIds = productIdJson,
        type = type,
        percent = percent,
        buyX = buyX,
        payY = payY,
        startAtEpoch = startAtEpoch,
        endAtEpoch = endAtEpoch
    )
}

fun PromotionEntity.toDomain(json: Json): Promotion? {

    val decodeProductIds = runCatching {
        json.decodeFromString(
            deserializer = ListSerializer(String.serializer()),
            string = productIds
        )
    }.getOrNull()

    val finalType = runCatching {
        PromotionType.valueOf(
            type.trim().uppercase()
        )
    }.getOrNull()

    if(finalType == null || decodeProductIds == null) return null

    val finalOfferValue = when (finalType){
        PERCENT -> percent
        BUY_X_PAY_Y -> payY
    }?.toDouble()

    finalOfferValue ?: return null

    return Promotion(
        id = id,
        productIds = decodeProductIds,
        type = finalType,
        value = finalOfferValue,
        buyQuantity = buyX,
        startTime = Instant.ofEpochSecond(startAtEpoch),
        endTime = Instant.ofEpochSecond(endAtEpoch)
    )
}
