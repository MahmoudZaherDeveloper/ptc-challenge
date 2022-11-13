package com.ptc.challenge.presentation.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.map
import com.google.android.material.snackbar.Snackbar
import com.ptc.challenge.BaseFragment
import com.ptc.challenge.R
import com.ptc.challenge.databinding.SearchFragmentBinding
import com.ptc.challenge.presentation.adapter.ProductsAdapterGrid
import com.ptc.challenge.presentation.MainViewModel

class SearchFragment : BaseFragment<SearchFragmentBinding>(SearchFragmentBinding::inflate) {
    private val productsAdapterGrid: ProductsAdapterGrid = ProductsAdapterGrid()
    private val viewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewProducts.adapter = productsAdapterGrid

        productsAdapterGrid.addLoadStateListener { state ->
            if (state.source.refresh is LoadState.NotLoading && state.append.endOfPaginationReached && productsAdapterGrid.itemCount < 1) {
                binding.noResults.root.visibility = View.VISIBLE
            } else {
                binding.noResults.root.visibility = View.GONE

            }

            when (state.source.refresh) {
                is LoadState.NotLoading -> {

                }
                LoadState.Loading -> {
                }
                is LoadState.Error -> {

                    Snackbar.make(
                        binding.root,
                        getString(R.string.error_something_went_wrong_internet_connection),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }

        }

        binding.textViewSearch.addTextChangedListener {
            lifecycleScope.launchWhenStarted {
                viewModel.setSearchKeyword(it.toString())
                listenToEvents()

            }
        }



        productsAdapterGrid.onItemClickListener {
            viewModel.setSku(it.sku)
            findNavController().navigate(R.id.action_searchFragment_to_productDetailsFragment)
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