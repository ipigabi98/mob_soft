package com.example.mobsoftapp.network

import com.example.mobsoftapp.model.Product
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.*

interface StoreService {

    @GET("products")
    suspend fun getProductList(): ApiResponse<List<Product>>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Long): ApiResponse<Product>

    @POST("products")
    suspend fun createProduct(@Body product: Product): ApiResponse<Product>

    @PUT("products/{id}")
    suspend fun updateProduct(@Path("id") id: Long, @Body product: Product): ApiResponse<Product>

    @DELETE("products/{id}")
    suspend fun deleteProductById(@Path("id") id: Long): ApiResponse<Product>

}