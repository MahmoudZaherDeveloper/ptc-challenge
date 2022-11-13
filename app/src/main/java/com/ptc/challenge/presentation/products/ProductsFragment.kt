package com.ptc.challenge.presentation.products

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import com.ptc.challenge.BaseFragment
import com.ptc.challenge.R
import com.ptc.challenge.databinding.ProductsFragmentBinding
import com.ptc.challenge.presentation.MainViewModel
import com.ptc.challenge.presentation.adapter.ProductsAdapterGrid

class ProductsFragment : BaseFragment<ProductsFragmentBinding>(ProductsFragmentBinding::inflate) {
    private val productsAdapterGrid: ProductsAdapterGrid = ProductsAdapterGrid()
    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPhones()
        listenToEvents()
        binding.recyclerViewProducts.adapter = productsAdapterGrid


        productsAdapterGrid.addLoadStateListener { state ->

            when (state.source.refresh) {
                is LoadState.NotLoading -> {

                }
                LoadState.Loading -> {

                }
                is LoadState.Error -> {

                    Snackbar.make(
                        binding.root,
                        getString(R.string.error_something_went_wrong_internet_connection),
                        Snackbar.LENGTH_INDEFINITE
                    ).show()
                }
            }

        }


        binding.textViewSearch.setOnClickListener {
            findNavController().navigate(R.id.action_products_to_searchFragment)

        }

        productsAdapterGrid.onItemClickListener {
            viewModel.setSku(it.sku)
            findNavController().navigate(R.id.action_products_to_productDetailsFragment)
        }
    }


    private fun listenToEvents() {
        lifecycleScope.launchWhenStarted {
            viewModel.productsState.collect { event ->
                productsAdapterGrid.submitData(event)
            }
        }
    }

}