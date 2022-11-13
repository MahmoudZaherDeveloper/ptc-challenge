package com.ptc.challenge.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ptc.challenge.R
import com.ptc.challenge.databinding.ProductImageGalleryBinding

class ProductImagesGallerySliderAdapter :
    RecyclerView.Adapter<ProductImagesGallerySliderAdapter.ViewHolder>() {


    private val differCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return newItem == oldItem

        }
    }
    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.product_image_gallery, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem: String = differ.currentList[position]
        holder.mItemBinding.image = currentItem
        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(position) }
        }

    }

    class ViewHolder(itemBinding: ProductImageGalleryBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val mItemBinding: ProductImageGalleryBinding = itemBinding

    }


    override fun getItemCount(): Int = differ.currentList.size

    private var onItemClickListener: ((Int) -> Unit)? = null

    fun onItemClickListener(item: (Int) -> Unit) {
        onItemClickListener = item
    }
}