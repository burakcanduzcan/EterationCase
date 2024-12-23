package com.burakcanduzcan.eterationnativedevelopmentstudycase.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.burakcanduzcan.eterationnativedevelopmentstudycase.R
import com.burakcanduzcan.eterationnativedevelopmentstudycase.databinding.FragmentFilterBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterBottomSheet(private val viewModel: HomeViewModel) : BottomSheetDialogFragment() {

    private var _binding: FragmentFilterBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnApplyFilter.setOnClickListener {
            val selectedSortOption = when (binding.rgSort.checkedRadioButtonId) {
                R.id.rb_old_to_new -> "oldToNew"
                R.id.rb_new_to_old -> "newToOld"
                R.id.rb_price_low_to_high -> "priceLowToHigh"
                R.id.rb_price_high_to_low -> "priceHighToLow"
                else -> null
            }

            selectedSortOption?.let {
                when (it) {
                    "oldToNew" -> viewModel.sortProductsByDate(true)
                    "newToOld" -> viewModel.sortProductsByDate(false)
                    "priceLowToHigh" -> viewModel.sortProductsByPrice(true)
                    "priceHighToLow" -> viewModel.sortProductsByPrice(false)
                    else -> null
                }
            }

            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}