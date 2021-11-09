package com.conlage.smartshopping.viewmodel.impl

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.conlage.smartshopping.model.data.local.Product
import com.conlage.smartshopping.model.data.local.ProductList
import com.conlage.smartshopping.model.data.local.db.entity.ShopItem
import com.conlage.smartshopping.model.data.usecase.impl.*
import com.conlage.smartshopping.model.data.usecase.wrapper.UseCaseResult
import com.conlage.smartshopping.viewmodel.MainViewModel
import com.conlage.smartshopping.viewmodel.base.BaseViewModel
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

    val productListState: MutableList<ShopItem> = mutableStateListOf()

    private val searchJobs: HashMap<Int, Job> = HashMap()
    private val deleteJobs: HashMap<Int, Job> = HashMap()

    //subscribe to data sources and observe the data
    init {
        getProductList()
    }


    override fun handleDialogState(shopItemTitle: String, state: Boolean) {
        if (state) {
            viewModelScope.launch(dispatcherMain + errHandler) {
                updateState {
                    it.copy(
                        isBulbOpen = true,
                        bulbTitle = shopItemTitle,
                        isLoadingBulb = true
                    )
                }

                val bulbList = getProductListFromNetwork(shopItemTitle)

                updateState {
                    it.copy(
                        bulbList = bulbList as MutableList<Product>,
                        isLoadingBulb = false
                    )
                }


            }
        } else {
            updateState {
                it.copy(
                    isBulbOpen = false,
                    bulbTitle = "",
                    isLoadingBulb = false
                )
            }
        }

    }

    override fun handleScannerButton() {
        updateState {
            it.copy(
                isScannerOpen = !it.isScannerOpen
            )
        }
    }


    override fun handleShopItemQuery(title: String) {

        updateState { it.copy(addItemTitle = title) }

    }


    override fun handlePermissionCount() {
        if (currentValue.permissionLaunchCounter > 1) {
            handleSnackbarPermission()
            Log.e("MainViewModel", "count ${currentValue.permissionLaunchCounter}", )
        } else updateState { it.copy(permissionLaunchCounter = it.permissionLaunchCounter + 1) }
        Log.e("MainViewModel", "count ${currentValue.permissionLaunchCounter}", )

    }

    override fun handleSnackbarPermission() {
        viewModelScope.launch(dispatcherMain + errHandler) {

            updateState {
                it.copy(
                    isShouldShowRationale = true
                )
            }

            delay(3000)

            updateState {
                it.copy(
                    isShouldShowRationale = false
                )
            }

        }
    }

    override fun handleNewShopItem() {
        if (currentValue.addItemTitle.isBlank()) return
        viewModelScope.launch(dispatcherMain + errHandler) {
            val shopItem = ShopItem(
                title = currentValue.addItemTitle
            )
            updateState {
                it.copy(
                    addItemTitle = ""
                )
            }

            saveUseCase.saveProductInDb(shopItem)
            productListState.add(0, shopItem)
        }
    }


    override fun getProductList() {

        viewModelScope.launch(dispatcherMain + errHandler) {
            currentValue.isLoadingProducts = true

            Log.e("GetProductList", "getting..")

            val list = when (val result = productListUseCase.getProductListFromDb()) {
                is UseCaseResult.Response<List<ShopItem>> -> result.value
                else -> mutableListOf()
            }


            Log.e("GetProductList", "$list")


            productListState.addAll(list)


            currentValue.isLoadingProducts = false


        }

    }

    override suspend fun getProductListFromNetwork(query: String): List<Product> =
        when (val result = productListUseCase.getProductList(query, currentValue.currentPage)) {
            is UseCaseResult.Response<ProductList> -> result.value.list
            else -> mutableListOf()
        }

    /**
     *  Not used methods, use it if you need
     */
//    override fun handleIncAddedProduct(product: Product) {
//        viewModelScope.launch(dispatcherMain + errHandler) {
//            if (productListState.containsId(product.id) && productListState.isNotEmpty()) {
//                val oldProduct = productListState.getProductById(product.id)
//                val index = productListState.indexOf(oldProduct)
//
//                val newProduct = oldProduct.copy(quantity = oldProduct.quantity + 1)
//                newProduct.bitmap = product.bitmap
//                productListState[index] = newProduct
//
//                productUpdateUseCase.updateProductInDb(newProduct)
//            } else {
//                productListState.add(0, product)
//                saveUseCase.saveProductInDb(product)
//            }
//        }
//
//    }

//    override fun handleDecAddedProduct(product: Product) {
//        viewModelScope.launch(dispatcherMain + errHandler) {
//            if (productListState.containsId(product.id) && productListState.isNotEmpty()) {
//                val oldProduct = productListState.getProductById(product.id)
//                val index = productListState.indexOf(oldProduct)
//
//                if (oldProduct.quantity > 1) {
//                    val newProduct = oldProduct.copy(quantity = oldProduct.quantity - 1)
//                    newProduct.bitmap = product.bitmap
//                    productListState[index] = newProduct
//                } else {
//                    productListState.removeAt(index)
//                    deleteUseCase.deleteProductFromDb(product)
//                }
//            }
//        }
//
//    }


//    override fun handleIncSearchItem(productIndex: Int, callback: (product: Product) -> Unit) {
//        viewModelScope.launch(dispatcherMain + errHandler) {
//            val currentList = currentValue.searchList
//
//            val replacedList = currentList.mapIndexed { i, product ->
//                if (productIndex == i) {
//
//                    val newQuantity = product.quantity + 1
//                    val newProduct = product.copy(quantity = newQuantity)
//
//                    newProduct.bitmap = product.bitmap
//
//                    callback(newProduct)
//
//                    newProduct
//                } else {
//                    product
//                }
//            }
//
//            updateState {
//                it.copy(
//                    searchList = replacedList as MutableList<Product>,
//                )
//            }
//
//
//        }
//    }

//    override fun handleDecSearchItem(productIndex: Int, callback: (product: Product) -> Unit) {
//
//        viewModelScope.launch(dispatcherMain + errHandler) {
//
//            if (currentValue.searchList[productIndex].quantity > 0) {
//                val currentList = currentValue.searchList
//
//                val replacedList = currentList.mapIndexed { i, product ->
//                    if (productIndex == i) {
//
//                        val newQuantity = product.quantity - 1
//                        val newProduct = product.copy(quantity = newQuantity)
//
//                        newProduct.bitmap = product.bitmap
//
//                        callback(newProduct)
//
//                        newProduct
//                    } else product
//                }
//                updateState { it.copy(searchList = replacedList as MutableList<Product>) }
//            }
//
//
//        }
//    }


    override fun handleProductCheckBox(shopItemIndex: Int, wantBeDeleted: Boolean) {
        val product = productListState[shopItemIndex]
        product.wantBeDeleted = !product.wantBeDeleted

        productListState.removeAt(shopItemIndex)
        productListState.add(shopItemIndex, product)


        if (product.wantBeDeleted) {
            val job = viewModelScope.launch(dispatcherMain + errHandler) {
                delay(5000)
                deleteUseCase.deleteProductFromDb(product)
                productListState.remove(product)
            }
            deleteJobs[shopItemIndex] = job
        } else {
            val job = deleteJobs[shopItemIndex] ?: return
            if (job.isActive) job.cancel()
        }
    }


    override fun handleSearchQuery(query: String) {
        Log.e("Search Query", "handleSearchQuery: $query")
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
                    delay(200)
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
        if (!isOpen) {
            searchJobs.forEach { job ->
                job.value.cancel()
            }
        }
        updateState {
            it.copy(
                isSearchOpen = isOpen,
                searchQuery = "",
                isLoadingSearchProducts = false,
                isSearchError = false
            )
        }
        currentValue.searchList.clear()
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
