package com.example.mobsoftapp.ui.details

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
class DetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository
) : ViewModel() {

    fun loadProduct(productId: Long): Product? {
        var product: Product;
        runBlocking {
            withContext(Dispatchers.IO) {
                product = detailRepository.loadProduct(productId).first()!!
            }
        }
        return product
    }

    fun deleteProduct(productId: Long): String {
        var result: String = ""
        runBlocking {
            withContext(Dispatchers.IO) {
                result = detailRepository.deleteProduct(productId).first()
            }
        }
        return result
    }
}