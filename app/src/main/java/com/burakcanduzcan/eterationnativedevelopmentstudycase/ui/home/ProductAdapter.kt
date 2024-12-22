package com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.burakcanduzcan.eterationnativedevelopmentstudycase.databinding.ItemProductBinding
import com.burakcanduzcan.eterationnativedevelopmentstudycase.model.ProductUiModel

class ProductAdapter : ListAdapter<ProductUiModel, ProductAdapter.ProductViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(uiModel: ProductUiModel) {
            binding.tvProductName.text = uiModel.name
            binding.tvProductPrice.text = uiModel.price
            Glide.with(binding.ivProductImage.context)
                .load(uiModel.imageUrl)
                .into(binding.ivProductImage)
            binding.btnAddToCart.setOnClickListener {
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<ProductUiModel>() {
            override fun areItemsTheSame(
                oldItem: ProductUiModel,
                newItem: ProductUiModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ProductUiModel,
                newItem: ProductUiModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}