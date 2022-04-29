package com.example.mobsoftapp.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.mobsoftapp.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    mainRepository: MainRepository
) : ViewModel() {

    private val productList: Flow<List<Product>> = mainRepository.loadProductList(
        onStart = { Log.d("ENYEM", it) },
        onCompletion = { Log.d("ENYEM", it) },
        onError = { Log.d("ENYEM", it) }
    )

    fun getProductList(): List<Product> {
        var products: List<Product> = listOf()
        runBlocking {
            withContext(Dispatchers.IO) {
                products = productList.first()
            }
        }
        return products
    }

}