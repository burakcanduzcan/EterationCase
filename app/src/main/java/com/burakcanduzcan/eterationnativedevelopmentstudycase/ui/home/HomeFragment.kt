package com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.burakcanduzcan.eterationnativedevelopmentstudycase.core.BaseFragment
import com.burakcanduzcan.eterationnativedevelopmentstudycase.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()
    private lateinit var productAdapter: ProductAdapter

    override fun initUi() {
        productAdapter = ProductAdapter()
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