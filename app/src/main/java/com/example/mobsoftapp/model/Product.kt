package com.example.mobsoftapp.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey val id: Long,
    var title: String,
    var price: Double,
    var description: String,
    var category: String,
    var image: String,
    @Embedded
    var rating: Rating
)