package com.ptc.challenge.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ptc.challenge.data.remote.Exceptions
import com.ptc.challenge.domain.model.Product
import com.ptc.challenge.domain.model.ProductDetails
import com.ptc.challenge.domain.use_case.ListingProducts
import com.ptc.challenge.domain.use_case.ViewProduct
import com.ptc.challenge.utils.DispatcherProvider
import com.ptc.challenge.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val listProduct: ListingProducts,
    private val viewProduct: ViewProduct,
    private val dispatchers: DispatcherProvider,
) : ViewModel() {


    private val _sku = MutableLiveData<String>()


    private val _productEvent = MutableStateFlow<ProductState?>(null)
    val productEvent = _productEvent.asStateFlow()


    var productsState: Flow<PagingData<Product>> = listProduct("phone")
        .cachedIn(viewModelScope)


    sealed class ProductState {
        object Loading : ProductState()
        data class Error(val e: Exceptions) : ProductState()
        data class Success(val product: ProductDetails) : ProductState()

    }


    fun setSku(sku: String) {
        _sku.postValue(sku)
        getProductDetails(sku)
    }

    private fun getProductDetails(sku: String) {
        viewModelScope.launch(dispatchers.io) {

            _productEvent.emit(ProductState.Loading)

            viewProduct(sku).collect {
                when (it) {
                    is Resource.Error -> {
                        _productEvent.emit(
                            ProductState.Error(
                                it.exception ?: Exceptions.ServerError
                            )
                        )
                    }
                    is Resource.Loading -> {
                        _productEvent.emit(ProductState.Loading)
                    }
                    is Resource.Success -> {
                        _productEvent.emit(ProductState.Success(it.data!!))
                    }
                }
            }
        }
    }

    fun setSearchKeyword(query: String) {
        if (query.isNotBlank()) {
            productsState = listProduct(query)
                .cachedIn(viewModelScope)
        }
    }

    fun getPhones() {
            productsState = listProduct("phone")
                .cachedIn(viewModelScope)
    }


}