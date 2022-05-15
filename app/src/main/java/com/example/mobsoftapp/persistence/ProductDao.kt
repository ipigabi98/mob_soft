package com.example.mobsoftapp.persistence

import androidx.room.*
import com.example.mobsoftapp.model.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductList(products: List<Product>)

    @Update
    suspend fun updateProduct(product: Product)

    @Query("SELECT * FROM Product WHERE id = :id_")
    suspend fun getProduct(id_: Long): Product?

    @Query("SELECT * FROM Product")
    suspend fun getProductList(): List<Product>

    @Query("DELETE FROM Product WHERE id = :productId")
    suspend fun deleteProduct(productId: Long)
}