package com.example.mobsoftapp.utils

import com.example.mobsoftapp.model.Product
import org.mockito.Mockito.mock

object MockTestUtil {
    fun mockProductList() = listOf(Product.mock())
}