package com.conlage.smartshopping.viewmodel.impl

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.conlage.smartshopping.model.data.local.ProductDetails
import com.conlage.smartshopping.model.data.usecase.impl.*
import com.conlage.smartshopping.model.data.usecase.wrapper.UseCaseResult
import com.conlage.smartshopping.viewmodel.ProductViewModel
import com.conlage.smartshopping.viewmodel.base.BaseViewModel
import com.conlage.smartshopping.viewmodel.state.ProductScreenState
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject


class ProductViewModelImpl @Inject constructor(
    private val productIdUseCase: ProductIdUseCaseImpl,
    private val productBarCodeUseCase: ProductBarCodeUseCaseImpl,
) : BaseViewModel<ProductScreenState>(ProductScreenState()), ProductViewModel {

    private val clearJobs = HashMap<Int, Job>()

    fun start(
        productId: Int,
        isAdded: Boolean = false,
        barcode: String? = null,
    ) {
        getProductDetails(productId, isAdded, barcode)
    }

    fun clear() {

        clearJobs.values.forEach { job->
            job.cancel()
        }

        updateState {
            it.copy(
                productDetails = null,
                isAdded = false,
                isEvaluation = false,
                isReadMore = false,
                isLoading = false,
                isClosing = true
            )
        }


    }

    override fun getProductDetails(productId: Int?, isAdded: Boolean, barcode: String?) {
        val job = viewModelScope.launch(dispatcherMain + errHandler) {
            updateState {
                it.copy(
                    isLoading = true, isClosing = false
                )
            }
            val value = when {
                productId == null -> {
                    return@launch
                }

                barcode == "null" -> {
                    getProductDetailsById(productId)
                }

                else -> getProductDetailsByBarcode(barcode)
            }

            delay(200)
            updateState {
                it.copy(
                    isLoading = false,
                    isAdded = isAdded,
                    productDetails = value
                )
            }


        }

        clearJobs[job.hashCode()] = job


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

    override suspend fun getProductDetailsById(productId: Int): ProductDetails? {
        return when (val result = productIdUseCase.getProductById(productId)) {
            is UseCaseResult.Response -> result.value
            is UseCaseResult.Error -> null
        }
    }

    override suspend fun getProductDetailsByBarcode(barcode: String?): ProductDetails? {
        return when (val result = productBarCodeUseCase.getProductByBarcode(barcode!!)) {
            is UseCaseResult.Response -> result.value
            is UseCaseResult.Error -> null
        }
    }


    class ProductViewModelFactory @AssistedInject constructor(
        private val productIdUseCase: ProductIdUseCaseImpl,
        private val productBarCodeUseCase: ProductBarCodeUseCaseImpl,
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductViewModelImpl::class.java))
                return ProductViewModelImpl(
                    productBarCodeUseCase = productBarCodeUseCase,
                    productIdUseCase = productIdUseCase
                ) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }

        @AssistedFactory
        interface ProductFactory {

            fun create(): ProductViewModelFactory

        }

    }


}

