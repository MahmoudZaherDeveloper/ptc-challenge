package com.ptc.challenge.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ptc.challenge.databinding.ProductGridLayoutBinding
import com.ptc.challenge.domain.model.Product

class ProductsAdapterGrid :
    PagingDataAdapter<Product, ProductsAdapterGrid.ViewHolder>(PAGE_DIFF_CALLBACK) {

    inner class ViewHolder(itemBinding: ProductGridLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private val mItemBinding: ProductGridLayoutBinding = itemBinding
        fun bind(product: Product) {
            mItemBinding.product = product
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position).apply {
            holder.itemView.setOnClickListener {
                holder.bind(this!!)
                onItemClickListener?.let { it(this) }
            }
        }!!)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ProductGridLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    companion object {
        private val PAGE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem.sku == newItem.sku

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }


    private var onItemClickListener: ((Product) -> Unit)? = null

    fun onItemClickListener(item: (Product) -> Unit) {
        onItemClickListener = item
    }
}