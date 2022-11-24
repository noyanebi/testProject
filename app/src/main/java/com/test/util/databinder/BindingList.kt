package com.test.util.databinder

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

import com.test.data.local.database.product.ProductEntity
import com.test.data.model.Product
import com.test.presentation.home.HomeAdapter
import com.test.presentation.product.ProductAdapter

class BindingList {

    companion object {

        @JvmStatic
        @BindingAdapter("setup_adapter")
        fun setItems(recyclerView: RecyclerView, items: List<Product>?) {
            val adapter = recyclerView.adapter as HomeAdapter?

            if (adapter != null && items != null) {
                adapter.swapData(items)
            }
        }

        @JvmStatic
        @BindingAdapter("setup_adapterProduct")
        fun setSubProducts(recyclerView: RecyclerView, items: List<Product>?) {
            val adapter = recyclerView.adapter as ProductAdapter?

            if (adapter != null && items != null) {
                adapter.swapData(items)
            }
        }
    }
}
