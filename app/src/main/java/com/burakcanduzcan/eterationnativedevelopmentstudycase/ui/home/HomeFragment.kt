package com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.burakcanduzcan.eterationnativedevelopmentstudycase.R
import com.burakcanduzcan.eterationnativedevelopmentstudycase.core.BaseFragment
import com.burakcanduzcan.eterationnativedevelopmentstudycase.databinding.FragmentHomeBinding
import com.burakcanduzcan.eterationnativedevelopmentstudycase.model.ProductUiModel
import com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun initUi() {
        binding.searchView.queryHint = getString(R.string.search)

        productAdapter = ProductAdapter(
            onProductClicked = { product: ProductUiModel ->
                safeClick {
                    Timber.d("Product clicked: $product")

                    findNavController().navigate(
                        R.id.action_navigation_home_to_navigation_product_detail,
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
            }
        )
        binding.rvProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = productAdapter
        }
    }

    override fun initListeners() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.searchProducts(it)
                }
                return true
            }
        })
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

        viewModel.filteredProducts.observe(viewLifecycleOwner) { products ->
            Timber.d("Filtered Products: $products")
            productAdapter.submitList(products)
        }
    }
}