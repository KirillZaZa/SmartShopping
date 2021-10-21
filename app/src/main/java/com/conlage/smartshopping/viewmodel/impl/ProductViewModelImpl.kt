package com.conlage.smartshopping.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.conlage.smartshopping.model.data.local.ProductDetails
import com.conlage.smartshopping.model.data.usecase.impl.*
import com.conlage.smartshopping.model.data.usecase.wrapper.UseCaseResult
import com.conlage.smartshopping.viewmodel.ProductViewModel
import com.conlage.smartshopping.viewmodel.base.BaseViewModel
import com.conlage.smartshopping.viewmodel.state.ProductScreenState
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

class ProductViewModelImpl @Inject constructor(
    private val deleteUseCase: ProductDeleteUseCaseImpl? = null,
    private val saveUseCase: ProductSaveUseCaseImpl? = null,
    private val productIdUseCase: ProductIdUseCaseImpl? = null,
    private val productBarCodeUseCase: ProductBarCodeUseCaseImpl? = null,
    private val barcode: String? = null,
    private val id: Int? = null
) : BaseViewModel<ProductScreenState>(ProductScreenState()), ProductViewModel {

    init {
        subscribeOnDataSource(getProductDetails()){ productDetails, state ->
            productDetails ?: return@subscribeOnDataSource null
            state.copy(
               product = productDetails
            )
        }
    }

    override fun getProductDetails(): ProductDetails? {
        viewModelScope.launch(dispatcherMain + errHandler) {
            updateState { it.copy(isLoading = true) }

            if (barcode == null) currentValue.product = getProductDetailsById()
            else currentValue.product = getProductDetailsByBarcode()

            updateState { it.copy(isLoading = false) }
        }
        return currentValue.product
    }


    override fun handleAddButton() {
        updateState {
            it.copy(
                isAdded = true
            )
        }
    }

    override fun handleDeleteButton() {
        updateState {
            it.copy(
                isAdded = false
            )
        }
    }

    override fun handleReadMore() {
        updateState {
            it.copy(
                isReadMore = !it.isReadMore
            )
        }
    }

    override fun handleAboutEvaluation() {
        updateState { it.copy(isEvaluation = !it.isEvaluation) }
    }

    override suspend fun getProductDetailsById(): ProductDetails? {
        return when (val result = productIdUseCase!!.getProductById(id!!)) {
            is UseCaseResult.Response -> result.value
            is UseCaseResult.Error -> null
        }
    }

    override suspend fun getProductDetailsByBarcode(): ProductDetails? {
        return when (val result = productBarCodeUseCase!!.getProductByBarcode(barcode!!)) {
            is UseCaseResult.Response -> result.value
            is UseCaseResult.Error -> null
        }
    }


}

class ProductViewModelFactory(
    private val id: Int?,
    private val barcode: String?
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductViewModelImpl::class.java))
            return ProductViewModelImpl(id = id, barcode = barcode) as T
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}