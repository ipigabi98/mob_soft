package com.example.mobsoftapp.ui.main

import com.example.mobsoftapp.network.StoreService
import com.example.mobsoftapp.persistence.ProductDao
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val storeService: StoreService,
    private val productDao: ProductDao
) {
}