package com.test.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductResponse(
    @SerializedName("categories")
    var categories: List<Product> = emptyList()
) : Parcelable

@Parcelize
data class Product(
    @SerializedName("label")
    var label: String,

    @SerializedName("url")
    var url: String? = null,

    @SerializedName("image")
    var image: String? = null,

    @SerializedName("children")
    var subProduct: List<Product> = emptyList(),

    ) : Parcelable