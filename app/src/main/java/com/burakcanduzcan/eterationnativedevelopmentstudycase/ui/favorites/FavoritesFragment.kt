package com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.burakcanduzcan.eterationnativedevelopmentstudycase.R
import com.burakcanduzcan.eterationnativedevelopmentstudycase.core.BaseFragment
import com.burakcanduzcan.eterationnativedevelopmentstudycase.databinding.FragmentFavoritesBinding
import com.burakcanduzcan.eterationnativedevelopmentstudycase.model.ProductUiModel
import com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.SharedViewModel
import com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.home.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

@AndroidEntryPoint
class FavoritesFragment :
    BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate) {

    override val viewModel: FavoritesViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.Default) {
            viewModel.refreshList()
        }
    }

    override fun initUi() {
        productAdapter = ProductAdapter(
            onProductClicked = { product: ProductUiModel ->
                safeClick {
                    Timber.d("Product clicked: $product")

                    findNavController().navigate(
                        R.id.action_navigation_favorites_to_navigation_product_detail,
                        Bundle().apply {
                            putParcelable("product", product)
                        }
                    )
                }
            },
            onAddToCartClicked = { product: ProductUiModel ->
                safeClick {
                    lifecycleScope.launch(Dispatchers.Default) {
                        viewModel.addToCart(product)
                        withContext(Dispatchers.Main) {
                            sharedViewModel.updateBasketCount(1)
                        }
                    }
                }
            },
            onFavoriteButtonClicked = {
                safeClick {
                    lifecycleScope.launch(Dispatchers.Default) {
                        viewModel.removeFromFavorite(it)
                    }
                }
            }
        )
        binding.rvProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productAdapter
        }
    }

    override fun initListeners() {
    }

    override fun initObservables() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            Timber.d("Products: $products")
            productAdapter.submitList(products)
        }

        viewModel.isFavoriteListEmpty.observe(viewLifecycleOwner) { isEmpty ->
            if (isEmpty) {
                binding.rvProducts.visibility = View.GONE
                binding.tvEmptyState.visibility = View.VISIBLE
            } else {
                binding.rvProducts.visibility = View.VISIBLE
                binding.tvEmptyState.visibility = View.GONE
            }
        }
    }
}