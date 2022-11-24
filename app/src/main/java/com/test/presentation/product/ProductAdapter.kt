package com.test.presentation.product

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.R
import com.test.data.local.database.product.ProductEntity
import com.test.data.model.Product

import com.test.databinding.ItemProductBinding
import com.test.databinding.ItemSubProductBinding

class ProductAdapter(
    private var products: List<Product>,
    var callback: (product: Product) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun swapData(dataList: List<Product>) {
        if (products !== dataList) {
            products = dataList[0].subProduct
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = DataBindingUtil.inflate<ItemSubProductBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_sub_product, parent, false        )

        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(rawHolder: RecyclerView.ViewHolder, position: Int) {
        val holder = rawHolder as ItemViewHolder
        holder.onBind(products[position])
    }

    override fun getItemCount() = products.size

    inner class ItemViewHolder(private val mBinding: ItemSubProductBinding) : RecyclerView.ViewHolder(mBinding.root) {

        fun onBind(product: Product) {
            mBinding.subProduct = product

            mBinding.tvTitle.setOnClickListener {

                callback.invoke(product)
            }    }
    }
}
