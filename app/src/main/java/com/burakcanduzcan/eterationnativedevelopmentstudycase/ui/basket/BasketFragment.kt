package com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.basket

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.burakcanduzcan.eterationnativedevelopmentstudycase.core.BaseFragment
import com.burakcanduzcan.eterationnativedevelopmentstudycase.databinding.FragmentBasketBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class BasketFragment : BaseFragment<FragmentBasketBinding>(FragmentBasketBinding::inflate) {

    override val viewModel: BasketViewModel by viewModels()
    private lateinit var basketAdapter: BasketAdapter

    override fun initUi() {
        basketAdapter = BasketAdapter(
            onProductAddClicked = {
                lifecycleScope.launch(Dispatchers.Default) {
                    viewModel.addToCart(it)
                }
            },
            onProductRemoveClicked = {
                lifecycleScope.launch(Dispatchers.Default) {
                    viewModel.removeFromCart(it)
                }
            })

        binding.rvBasket.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = basketAdapter
        }
    }

    override fun initListeners() {
    }

    override fun initObservables() {
        viewModel.basketProducts.observe(viewLifecycleOwner) { products ->
            Timber.d("Basket products: $products")
            basketAdapter.submitList(products)
            viewModel.calculateTotalValue()
        }

        viewModel.totalValue.observe(viewLifecycleOwner) { totalValue ->
            Timber.d("Total value: $totalValue")
            binding.tvPrice.text = totalValue.toString().removeSuffix(".0") + " ₺"
        }
    }
}