package com.test.data.local.database.product

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.test.data.model.Product

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    var label: String,
    var url: String? = null,
    var image: String? = null,

    )