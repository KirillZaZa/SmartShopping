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
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import okio.ByteString.Companion.decodeBase64
import java.lang.IllegalArgumentException


class ProductViewModelImpl(
    private val productIdUseCase: ProductIdUseCaseImpl,
    private val productBarCodeUseCase: ProductBarCodeUseCaseImpl,
    private val barcode: String? = null,
    private val id: Int? = null,
    private val isAdded: Boolean = false
) : BaseViewModel<ProductScreenState>(ProductScreenState()), ProductViewModel {

    init {
        subscribeOnDataSource(getProductDetails()) { productDetails, state ->
            productDetails ?: return@subscribeOnDataSource null
            state.copy(
                productDetails = productDetails,
            )
        }

    }

    override fun getProductDetails(): ProductDetails? {
        viewModelScope.launch(dispatcherMain + errHandler) {
            updateState { it.copy(isLoading = true) }

            if (barcode.isNullOrBlank()) {
                currentValue.productDetails = getProductDetailsById()
            } else {
                currentValue.productDetails = getProductDetailsByBarcode()
            }



            updateState {
                it.copy(
                    isLoading = false,
                    isAdded = isAdded
                )
            }
        }
        return currentValue.productDetails
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
        return when (val result = productIdUseCase.getProductById(id!!)) {
            is UseCaseResult.Response -> result.value
            is UseCaseResult.Error -> null
        }
    }

    override suspend fun getProductDetailsByBarcode(): ProductDetails? {
        return when (val result = productBarCodeUseCase.getProductByBarcode(barcode!!)) {
            is UseCaseResult.Response -> result.value
            is UseCaseResult.Error -> null
        }
    }


    class ProductViewModelFactory @AssistedInject constructor(
        @Assisted("product_id") private val id: Int?,
        @Assisted("product_barcode") private val barcode: String?,
        @Assisted("product_is_added") private val isAdded: Boolean,
        private val productIdUseCase: ProductIdUseCaseImpl,
        private val productBarCodeUseCase: ProductBarCodeUseCaseImpl,
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductViewModelImpl::class.java))
                return ProductViewModelImpl(
                    isAdded = isAdded,
                    id = id,
                    barcode = barcode,
                    productBarCodeUseCase = productBarCodeUseCase,
                    productIdUseCase = productIdUseCase
                ) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }

        @AssistedFactory
        interface Factory {

            fun create(
                @Assisted("product_id") id: Int?,
                @Assisted("product_barcode") barcode: String?,
                @Assisted("product_is_added") isAdded: Boolean
            ): ProductViewModelFactory

        }

    }


}

