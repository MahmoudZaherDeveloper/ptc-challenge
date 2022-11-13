package com.ptc.challenge.presentation.product_view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.ptc.challenge.BaseFragment
import com.ptc.challenge.R
import com.ptc.challenge.data.remote.Exceptions
import com.ptc.challenge.databinding.ProductDetailsFragmentBinding
import com.ptc.challenge.presentation.adapter.ProductImagesGalleryAdapter
import com.ptc.challenge.presentation.adapter.ProductImagesGallerySliderAdapter
import com.ptc.challenge.presentation.MainViewModel

class ProductDetailsFragment :
    BaseFragment<ProductDetailsFragmentBinding>(ProductDetailsFragmentBinding::inflate) {
    private val viewModel: MainViewModel by activityViewModels()
    private val productImagesGalleryAdapter: ProductImagesGallerySliderAdapter =
        ProductImagesGallerySliderAdapter()

    private val productImagesAdapter: ProductImagesGalleryAdapter =
        ProductImagesGalleryAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenToEvents()
        binding.viewPager.adapter = productImagesGalleryAdapter
        binding.recyclerViewGallery.adapter = productImagesAdapter

        productImagesGalleryAdapter.onItemClickListener {
            binding.recyclerViewGallery.smoothScrollToPosition(it)
        }

        productImagesAdapter.onItemClickListener {
            binding.viewPager.currentItem = it
        }
    }


    private fun listenToEvents() {
        lifecycleScope.launchWhenStarted {
            viewModel.productEvent.collect { event ->
                when (event) {
                    is MainViewModel.ProductState.Error -> {
                        binding.shimmer?.root?.visibility = View.GONE

                        when (event.e) {

                            Exceptions.NoInternet -> {
                                Snackbar.make(
                                    binding.root,
                                    getString(R.string.error_something_went_wrong_internet_connection),
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                            Exceptions.NotFound -> {
                                binding.notAvailable.root.visibility = View.VISIBLE

                            }
                            Exceptions.ServerError -> {
                                Snackbar.make(
                                    binding.root,
                                    getString(R.string.error_something_went_wrong),
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                    is MainViewModel.ProductState.Loading -> Unit
                    is MainViewModel.ProductState.Success -> {
                        binding.productDataGroup.visibility = View.VISIBLE
                        binding.notAvailable.root.visibility = View.GONE
                        binding.shimmer?.root?.visibility = View.GONE
                        productImagesGalleryAdapter.differ.submitList(event.product.images)
                        productImagesAdapter.differ.submitList(event.product.images)
                        binding.product = event.product

                    }
                    null -> Unit
                }
            }
        }
    }

}