package com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.product_detail

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.burakcanduzcan.eterationnativedevelopmentstudycase.core.BaseFragment
import com.burakcanduzcan.eterationnativedevelopmentstudycase.databinding.FragmentProductDetailBinding
import com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.MainActivity
import com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ProductDetailFragment :
    BaseFragment<FragmentProductDetailBinding>(FragmentProductDetailBinding::inflate) {

    override val viewModel: ProductDetailViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

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
                    lifecycleScope.launch(Dispatchers.Default) {
                        viewModel.addToCart(product)
                        withContext(Dispatchers.Main) {
                            sharedViewModel.updateBasketCount(1)
                        }
                    }
                }
            }
        }
    }
}