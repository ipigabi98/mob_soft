package com.example.mobsoftapp.ui.details

import android.util.Log
import androidx.annotation.WorkerThread
import com.example.mobsoftapp.model.Product
import com.example.mobsoftapp.persistence.ProductDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val productDao: ProductDao
) {

    @WorkerThread
    fun loadProduct(
        productId: Long,
    ) = flow {
        emit(productDao.getProduct(productId))
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun deleteProduct(productId: Long) = flow {
        productDao.deleteProduct(productId)
        emit("Deleted")
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun saveProduct(product: Product) = flow {
        productDao.insertProduct(product)
        emit("Inserted")
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun updateProduct(product: Product) = flow {
        productDao.updateProduct(product)
        emit("Updated")
    }.flowOn(Dispatchers.IO)
}