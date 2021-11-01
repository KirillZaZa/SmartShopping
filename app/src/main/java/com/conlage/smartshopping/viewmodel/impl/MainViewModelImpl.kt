package com.conlage.smartshopping.viewmodel.impl

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.model.data.local.db.entity.ProductList
import com.conlage.smartshopping.model.data.usecase.impl.*
import com.conlage.smartshopping.model.data.usecase.wrapper.UseCaseResult
import com.conlage.smartshopping.viewmodel.MainViewModel
import com.conlage.smartshopping.viewmodel.base.BaseViewModel
import com.conlage.smartshopping.viewmodel.events.DeleteEvent
import com.conlage.smartshopping.viewmodel.events.SaveEvent
import com.conlage.smartshopping.viewmodel.extension.containsId
import com.conlage.smartshopping.viewmodel.extension.getProductById
import com.conlage.smartshopping.viewmodel.state.MainScreenState
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import java.lang.IllegalArgumentException
import javax.inject.Inject


class MainViewModelImpl @Inject constructor(
    private val deleteUseCase: ProductDeleteUseCaseImpl,
    private val saveUseCase: ProductSaveUseCaseImpl,
    private val productListUseCase: ProductListUseCaseImpl,
    private val productUpdateUseCase: ProductUpdateUseCaseImpl,
) : BaseViewModel<MainScreenState>(MainScreenState()), MainViewModel {

    val productListState: MutableList<Product> = mutableStateListOf()

    private val searchJobs: HashMap<Int, Job> = HashMap()
    private val deleteJobs: HashMap<Int, Job> = HashMap()

    //subscribe to data sources and observe the data
    init {
        productListState.addAll(getProductList())
    }


    override fun getProductList(): List<Product> {
        var list: List<Product> = mutableListOf()

        viewModelScope.launch(dispatcherMain + errHandler) {
            currentValue.isLoadingProducts = true

            list = when (val result = productListUseCase.getProductListFromDb()) {
                is UseCaseResult.Response<List<Product>> -> result.value
                else -> mutableListOf()
            }

            currentValue.isLoadingProducts = false
        }

        return list
    }

    override suspend fun getProductListFromNetwork(query: String): List<Product> =
        when (val result = productListUseCase.getProductList(query, currentValue.currentPage)) {
            is UseCaseResult.Response<ProductList> -> result.value.list
            else -> mutableListOf()
        }




    override fun handleIncAddedProduct(product: Product) {
        viewModelScope.launch(dispatcherMain + errHandler) {
            if(productListState.containsId(product.id) && productListState.isNotEmpty()){
                val oldProduct = productListState.getProductById(product.id)
                val index = productListState.indexOf(oldProduct)

                val newProduct = oldProduct.copy(quantity = oldProduct.quantity + 1)
                newProduct.bitmap = product.bitmap
                productListState[index] = newProduct
            }else{
                productListState.add(0, product)
            }
        }

    }

    override fun handleDecAddedProduct(product: Product) {
        viewModelScope.launch(dispatcherMain + errHandler) {
            if(productListState.containsId(product.id) && productListState.isNotEmpty()){
                val oldProduct = productListState.getProductById(product.id)
                val index = productListState.indexOf(oldProduct)

                if(oldProduct.quantity > 1){
                    val newProduct = oldProduct.copy(quantity = oldProduct.quantity - 1)
                    newProduct.bitmap = product.bitmap
                    productListState[index] = newProduct
                }else productListState.removeAt(index)
            }
        }

    }


    override fun handleIncSearchItem(productIndex: Int, callback: (product: Product) -> Unit) {
        viewModelScope.launch(dispatcherMain + errHandler) {
            val currentList = currentValue.searchList

            val replacedList = currentList.mapIndexed { i, product ->
                if(productIndex == i){

                    val newQuantity = product.quantity + 1
                    val newProduct = product.copy(quantity = newQuantity)

                    newProduct.bitmap = product.bitmap

                    callback(newProduct)

                    newProduct
                }else {
                    product
                }
            }

            updateState { it.copy(
                searchList = replacedList as MutableList<Product>,
            ) }




            val product = currentValue.searchList[productIndex]

            saveUseCase.saveProductInDb(product)

        }
    }

    override fun handleDecSearchItem(productIndex: Int, callback: (product: Product) -> Unit) {

        viewModelScope.launch(dispatcherMain + errHandler) {

            if (currentValue.searchList[productIndex].quantity > 0) {
                val currentList = currentValue.searchList

                val replacedList = currentList.mapIndexed { i, product ->
                    if(productIndex == i){

                        val newQuantity = product.quantity - 1
                        val newProduct = product.copy(quantity = newQuantity)

                        newProduct.bitmap = product.bitmap

                        callback(newProduct)

                        newProduct
                    }else product
                }
                updateState { it.copy(searchList = replacedList as MutableList<Product>) }
            }

            if (currentValue.searchList[productIndex].quantity == 0) {
                val product = currentValue.searchList[productIndex]
                handleDeleteProductById(product.id)
            } else {

                val product = currentValue.searchList[productIndex]

                productUpdateUseCase.updateProductInDb(product)

            }

        }
    }


    override fun handleProductCheckBox(productIndex: Int) {
        val product = currentValue.productList[productIndex]
        product.wantBeDeleted = !product.wantBeDeleted

        if (product.wantBeDeleted) {
            val job = viewModelScope.launch(dispatcherMain + errHandler) {
                delay(5000)
                deleteUseCase.deleteProductFromDb(product)
                currentValue.productList.remove(product)
            }
            deleteJobs[productIndex] = job
        } else {
            val job = deleteJobs[productIndex] ?: return
            if (job.isActive) job.cancel()
        }
    }


    override fun handleSearchQuery(query: String) {
        Log.e("Search Query", "handleSearchQuery: $query", )
        searchJobs.forEach {
            it.value.cancel()
        }

        updateState {
            it.copy(
                searchQuery = query,
                isSearchOpen = true,
                isLoadingSearchProducts = true
            )
        }



        if (query.length > 2) {


            val job = viewModelScope.launch(dispatcherMain + errHandler) {
                delay(100)

                currentValue.searchList.clear()
                Log.d("Scope", "handleSearchQuery: $currentValue ")

                updateState { it.copy(isLoadingSearchProducts = true) }

                val list = getProductListFromNetwork(query)
                if (list.isNullOrEmpty()) updateState {
                    it.copy(
                        isLoadingSearchProducts = false,
                        isSearchError = true
                    )
                } else {
                    updateState {
                        it.copy(
                            searchList = list as MutableList<Product>,
                            isLoadingSearchProducts = false,
                            isSearchError = false
                        )
                    }
                }


            }


            val queryLength = currentValue.searchQuery.length
            searchJobs[queryLength] = job

        } else {
            updateState { it.copy(isLoadingSearchProducts = false, isSearchError = false) }
            if (currentValue.searchList.isNullOrEmpty()) return
            else currentValue.searchList.clear()
        }


    }


    override fun handleSearchOpen(isOpen: Boolean) {
        if(!isOpen){
            searchJobs.forEach { job ->
                job.value.cancel()
            }
        }
        updateState {
            it.copy(
                isSearchOpen = isOpen,
                searchQuery = "",
                isSearchError = false
            )
        }
        currentValue.searchList.clear()
    }


    override fun handleCameraPermission(isGranted: Boolean) {
        updateState { it.copy(isCameraGranted = isGranted) }
    }

    override fun handleStoragePermission(isGranted: Boolean) {
        updateState { it.copy(isStorageGranted = isGranted) }
    }


    override fun handleNewPage() {
        viewModelScope.launch(dispatcherMain + errHandler) {

            updateState { it.copy(currentPage = it.currentPage.inc()) }

            val newPageList = getProductListFromNetwork(currentValue.searchQuery)

            with(currentValue) {
                searchList.addAll(searchList.size, newPageList)
            }

        }
    }

    override fun handleSaveProduct(productId: Int) {
        if (currentValue.productList.containsId(productId)) return
        viewModelScope.launch(dispatcherMain + errHandler) {
            val product = currentValue.searchList.getProductById(productId)

            saveUseCase.saveProductInDb(product)

            currentValue.productList.add(product)

            val index = currentValue.productList.indexOf(product)

            handleEvent(SaveEvent(index))
        }
    }

    override fun handleDeleteProductById(productId: Int) {
        if (!currentValue.productList.containsId(productId)) return
        viewModelScope.launch(dispatcherMain + errHandler) {
            val product = currentValue.productList.getProductById(productId)
            val index = currentValue.productList.indexOf(product)

            deleteUseCase.deleteProductFromDb(product)

            handleEvent(DeleteEvent(index))

            currentValue.productList.remove(product)
        }
    }


    class MainViewModelFactory @AssistedInject constructor(
        private val deleteUseCase: ProductDeleteUseCaseImpl,
        private val saveUseCase: ProductSaveUseCaseImpl,
        private val productListUseCase: ProductListUseCaseImpl,
        private val productUpdateUseCase: ProductUpdateUseCaseImpl,
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModelImpl::class.java))
                return MainViewModelImpl(
                    deleteUseCase,
                    saveUseCase,
                    productListUseCase,
                    productUpdateUseCase,
                ) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }

        @AssistedFactory
        interface Factory {

            fun create(): MainViewModelFactory

        }

    }

}
