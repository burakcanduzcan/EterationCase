package com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.basket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.burakcanduzcan.eterationnativedevelopmentstudycase.data.local.entity.BasketProductEntity
import com.burakcanduzcan.eterationnativedevelopmentstudycase.databinding.ItemBasketBinding

class BasketAdapter(
    private val onProductAddClicked: (BasketProductEntity) -> Unit,
    private val onProductRemoveClicked: (BasketProductEntity) -> Unit
) : ListAdapter<BasketProductEntity, BasketAdapter.BasketViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BasketAdapter.BasketViewHolder {
        val binding = ItemBasketBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return BasketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BasketAdapter.BasketViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BasketViewHolder(
        private val binding: ItemBasketBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(uiModel: BasketProductEntity) {
            binding.tvProductName.text = uiModel.name
            binding.tvProductPrice.text = uiModel.price.removeSuffix(".00") + " ₺"
            binding.tvProductQuantity.text = uiModel.basketQuantity.toString()
            binding.btnAdd.setOnClickListener {
                onProductAddClicked(uiModel)
            }
            binding.btnReduce.setOnClickListener {
                onProductRemoveClicked(uiModel)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<BasketProductEntity>() {
            override fun areItemsTheSame(
                oldItem: BasketProductEntity,
                newItem: BasketProductEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: BasketProductEntity,
                newItem: BasketProductEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
