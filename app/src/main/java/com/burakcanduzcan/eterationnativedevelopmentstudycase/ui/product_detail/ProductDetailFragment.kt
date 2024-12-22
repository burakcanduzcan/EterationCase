package com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.product_detail

import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.burakcanduzcan.eterationnativedevelopmentstudycase.core.BaseFragment
import com.burakcanduzcan.eterationnativedevelopmentstudycase.databinding.FragmentProductDetailBinding
import com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailFragment :
    BaseFragment<FragmentProductDetailBinding>(FragmentProductDetailBinding::inflate) {

    override val viewModel: ProductDetailViewModel by viewModels()

    override fun initUi() {
        viewModel.setProduct(arguments?.getParcelable("product"))
    }

    override fun initListeners() {
    }

    override fun initObservables() {
        viewModel.product.observe(viewLifecycleOwner) { product ->
            if (product != null) {
                (activity as? MainActivity)?.setAppBarTitle(product.name)

                Glide
                    .with(this)
                    .load(product.imageUrl)
                    .into(binding.ivProductImage)

                binding.tvTitle.text = product.name
                binding.tvDescription.text = product.description
                binding.tvPrice.text = product.price.removeSuffix(".00") + " ₺"

                binding.btnAddToCart.setOnClickListener {
                    viewModel.addToCart(product)
                }
            }
        }
    }
}