package com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.product_detail

import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.burakcanduzcan.eterationnativedevelopmentstudycase.R
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
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.setProduct(arguments?.getParcelable("product"))
            withContext(Dispatchers.Default) {
                if (viewModel.isProductInFavorite()) {
                    binding.ivStar.setColorFilter(
                        ContextCompat.getColor(requireContext(), R.color.yellow)
                    )
                }
            }
        }
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

                binding.ivStar.setOnClickListener {
                    safeClick {
                        lifecycleScope.launch(Dispatchers.Default) {
                            if (viewModel.isProductInFavorite()) {
                                viewModel.removeFromFavorite(product)
                                binding.ivStar.setColorFilter(
                                    ContextCompat.getColor(requireContext(), R.color.gray)
                                )
                            } else {
                                viewModel.addToFavorite(product)
                                binding.ivStar.setColorFilter(
                                    ContextCompat.getColor(requireContext(), R.color.yellow)
                                )
                            }
                        }
                    }
                }

                binding.tvTitle.text = product.name
                binding.tvDescription.text = product.description
                binding.tvPrice.text = binding.root.context.getString(
                    R.string.price_format,
                    product.price.removeSuffix(".00"),
                    binding.root.context.getString(R.string.currency_symbol)
                )

                binding.btnAddToCart.setOnClickListener {
                    safeClick {
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
}