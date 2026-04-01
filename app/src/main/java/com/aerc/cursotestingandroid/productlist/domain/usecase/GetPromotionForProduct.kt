package com.aerc.cursotestingandroid.productlist.domain.usecase

import com.aerc.cursotestingandroid.core.extensions.round2Decimals
import com.aerc.cursotestingandroid.productlist.domain.model.Product
import com.aerc.cursotestingandroid.productlist.domain.model.ProductPromotion
import com.aerc.cursotestingandroid.productlist.domain.model.Promotion
import com.aerc.cursotestingandroid.productlist.domain.model.PromotionType
import javax.inject.Inject

class GetPromotionForProduct @Inject constructor() {
    suspend operator fun invoke(product: Product, promotions: List<Promotion>): ProductPromotion? {
        val productPromos = promotions.filter { it.productIds.contains(product.id) }

        val percentPromo = productPromos.filter { it.type == PromotionType.PERCENT }
            .maxByOrNull { it.value }

        if (percentPromo != null) {
            val percent = percentPromo.value.coerceIn(0.0, 100.0)
            val discountPrice = (product.price * (1 - percent / 100.0)).round2Decimals()
            return ProductPromotion.Percent(percent = percent, discountedPrice = discountPrice)
        }

        val buyPayPromo = productPromos.firstOrNull { it.type == PromotionType.BUY_X_PAY_Y }

        if (buyPayPromo != null) {
            val buy = buyPayPromo.buyQuantity ?: return null
            val pay = buyPayPromo.value.toInt().coerceIn(0, buy)

            return ProductPromotion.BuyXPayY(
                buy = buy,
                pay = pay,
                label = "${buy}x${pay}"
            )
        }

        return null
    }
}
