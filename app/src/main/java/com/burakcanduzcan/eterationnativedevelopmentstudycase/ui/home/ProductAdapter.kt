package com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.burakcanduzcan.eterationnativedevelopmentstudycase.R
import com.burakcanduzcan.eterationnativedevelopmentstudycase.databinding.ItemProductBinding
import com.burakcanduzcan.eterationnativedevelopmentstudycase.model.ProductUiModel

class ProductAdapter(
    private val onProductClicked: (ProductUiModel) -> Unit,
    private val onAddToCartClicked: (ProductUiModel) -> Unit,
    private val onFavoriteButtonClicked: (ProductUiModel) -> Unit
) : ListAdapter<ProductUiModel, ProductAdapter.ProductViewHolder>(DiffCallback) {

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
            binding.clProduct.setOnClickListener {
                onProductClicked(uiModel)
            }

            if (uiModel.isFavorite) {
                binding.ivFavoriteButton.setColorFilter(
                    ContextCompat.getColor(binding.ivFavoriteButton.context, R.color.yellow)
                )
            } else {
                binding.ivFavoriteButton.setColorFilter(
                    ContextCompat.getColor(binding.ivFavoriteButton.context, R.color.gray)
                )
            }

            binding.ivFavoriteButton.setOnClickListener {
                if (uiModel.isFavorite) {
                    binding.ivFavoriteButton.setColorFilter(
                        ContextCompat.getColor(binding.ivFavoriteButton.context, R.color.gray)
                    )
                } else {
                    binding.ivFavoriteButton.setColorFilter(
                        ContextCompat.getColor(binding.ivFavoriteButton.context, R.color.yellow)
                    )
                }
                onFavoriteButtonClicked(uiModel)
            }

            binding.tvProductName.text = uiModel.name

            binding.tvProductPrice.text = binding.root.context.getString(
                R.string.price_format,
                uiModel.price.removeSuffix(".00"),
                binding.root.context.getString(R.string.currency_symbol)
            )

            Glide.with(binding.ivProductImage.context)
                .load(uiModel.imageUrl)
                .into(binding.ivProductImage)

            binding.btnAddToCart.setOnClickListener {
                onAddToCartClicked(uiModel)
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