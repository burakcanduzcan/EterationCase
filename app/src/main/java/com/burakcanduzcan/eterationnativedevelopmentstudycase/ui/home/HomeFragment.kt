package com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.burakcanduzcan.eterationnativedevelopmentstudycase.R
import com.burakcanduzcan.eterationnativedevelopmentstudycase.core.BaseFragment
import com.burakcanduzcan.eterationnativedevelopmentstudycase.databinding.FragmentHomeBinding
import com.burakcanduzcan.eterationnativedevelopmentstudycase.model.ProductUiModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun initUi() {
        productAdapter = ProductAdapter(
            onProductClicked = { product: ProductUiModel ->
                Timber.d("Product clicked: $product")

                findNavController().navigate(
                    R.id.action_navigation_home_to_navigation_product_detail,
                    Bundle().apply {
                        putParcelable("product", product)
                    }
                )
            },
            onAddToCartClicked = { product: ProductUiModel ->
                viewModel.addToCart(product)
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

        viewModel.isRemoteListEmpty.observe(viewLifecycleOwner) { isEmpty ->
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