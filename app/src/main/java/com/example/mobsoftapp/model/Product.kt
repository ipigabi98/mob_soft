package com.example.mobsoftapp.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    var title: String,
    var price: Double,
    var description: String,
    var category: String,
    var image: String,
    @Embedded
    var rating: Rating
) {

    companion object {
        fun mock() = Product(
            id = 1,
            title = "Nike Shoes",
            price = 200.0,
            description = "description",
            category = "category",
            image = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg",
            rating = Rating(rate = 10.0, count = 100)
        )
    }
}