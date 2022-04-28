package com.example.mobsoftapp.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mobsoftapp.model.Product

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductList(products: List<Product>)

    @Query("SELECT * FROM Product WHERE id = :id_")
    suspend fun getProduct(id_: Long): Product?

    @Query("SELECT * FROM Product")
    suspend fun getProductList(): List<Product>
}