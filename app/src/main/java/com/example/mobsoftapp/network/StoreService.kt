package com.example.mobsoftapp.network

import com.example.mobsoftapp.model.Product
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface StoreService {

    @GET("products")
    suspend fun getProductList(): ApiResponse<List<Product>>

}