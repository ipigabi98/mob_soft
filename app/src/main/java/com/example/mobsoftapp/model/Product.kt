package com.example.mobsoftapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey val id: Long,
    var name: String
)