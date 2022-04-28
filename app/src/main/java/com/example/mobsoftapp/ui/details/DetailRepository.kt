package com.example.mobsoftapp.ui.details

import com.example.mobsoftapp.persistence.ProductDao
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val productDao: ProductDao
) {
}