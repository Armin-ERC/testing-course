package com.aerc.cursotestingandroid.productlist.data.remote

import com.aerc.cursotestingandroid.productlist.data.remote.response.ProductsResponse
import com.aerc.cursotestingandroid.productlist.data.remote.response.PromotionsResponse
import retrofit2.http.GET

interface MiniMarketApiService {
    @GET("data/v1/products.json")
    suspend fun getProducts(): ProductsResponse

    @GET("data/v1/promotions.json")
    suspend fun getPromotions(): PromotionsResponse
}
