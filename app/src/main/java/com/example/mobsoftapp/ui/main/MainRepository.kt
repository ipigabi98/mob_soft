package com.example.mobsoftapp.ui.main

import androidx.annotation.WorkerThread
import com.example.mobsoftapp.model.Product
import com.example.mobsoftapp.network.StoreService
import com.example.mobsoftapp.persistence.ProductDao
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val storeService: StoreService,
    private val productDao: ProductDao
) {

    @WorkerThread
    fun loadProductList(
        onStart: (String) -> Unit,
        onCompletion: (String) -> Unit,
        onError: (String) -> Unit
    ) = flow {
        val products: List<Product> = productDao.getProductList()
        if (products.isEmpty()) {

            storeService.getProductList()
                .suspendOnSuccess {
                    productDao.insertProductList(data)
                    emit(data)
                }

                .onError {
                    onError(message())
                }

                .onException {
                    onError(message())
                }

        } else {
            emit(products)
        }
    }.onStart { onStart("Started.") }.onCompletion { onCompletion("Completed.") }.flowOn(Dispatchers.IO)
}