package com.test.presentation.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.R

import com.test.data.local.database.product.ProductEntity
import com.test.data.model.Product

import com.test.databinding.ItemProductBinding

class HomeAdapter(
    private var products: List<Product>,
    var callback: (product: Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun swapData(dataList: List<Product>) {
        if (products !== dataList) {
            products = dataList
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemProductBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_product, parent, false        )

        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(rawHolder: RecyclerView.ViewHolder, position: Int) {
        val holder = rawHolder as ItemViewHolder
        holder.onBind(products[position])
    }

    override fun getItemCount() = products.size

    inner class ItemViewHolder(private val mBinding: ItemProductBinding) : RecyclerView.ViewHolder(mBinding.root) {

        fun onBind(product: Product) {
            mBinding.product = product

            mBinding.card.setOnClickListener {
                Log.d("getHttpError", "ddd")
                callback.invoke(product)
            }    }
    }
}
