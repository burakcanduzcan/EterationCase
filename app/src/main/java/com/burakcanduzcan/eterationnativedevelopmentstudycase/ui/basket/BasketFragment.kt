package com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.basket

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.burakcanduzcan.eterationnativedevelopmentstudycase.R
import com.burakcanduzcan.eterationnativedevelopmentstudycase.core.BaseFragment
import com.burakcanduzcan.eterationnativedevelopmentstudycase.databinding.FragmentBasketBinding
import com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.MainActivity
import com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

@AndroidEntryPoint
class BasketFragment : BaseFragment<FragmentBasketBinding>(FragmentBasketBinding::inflate) {

    override val viewModel: BasketViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var basketAdapter: BasketAdapter

    override fun initUi() {
        (activity as? MainActivity)?.setAppBarTitle(getString(R.string.title_home))

        basketAdapter = BasketAdapter(
            onProductAddClicked = {
                safeClick {
                    lifecycleScope.launch(Dispatchers.Default) {
                        viewModel.addToCart(it)
                        withContext(Dispatchers.Main) {
                            sharedViewModel.updateBasketCount(1)
                        }
                    }
                }
            },
            onProductRemoveClicked = {
                safeClick {
                    lifecycleScope.launch(Dispatchers.Default) {
                        viewModel.removeFromCart(it)
                        withContext(Dispatchers.Main) {
                            sharedViewModel.updateBasketCount(-1)
                        }
                    }
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
            viewModel.setBasketEmpty(products.isEmpty())
        }

        viewModel.totalValue.observe(viewLifecycleOwner) { totalValue ->
            Timber.d("Total value: $totalValue")
            binding.tvPrice.text = binding.root.context.getString(
                R.string.price_format,
                totalValue.toString().removeSuffix(".0"),
                binding.root.context.getString(R.string.currency_symbol)
            )
        }

        viewModel.isBasketEmpty.observe(viewLifecycleOwner) { isEmpty ->
            if (isEmpty) {
                binding.rvBasket.visibility = View.GONE
                binding.tvEmptyState.visibility = View.VISIBLE
            } else {
                binding.rvBasket.visibility = View.VISIBLE
                binding.tvEmptyState.visibility = View.GONE
            }
        }
    }
}