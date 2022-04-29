package com.example.mobsoftapp.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobsoftapp.model.Product

@Database(entities = [Product::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}