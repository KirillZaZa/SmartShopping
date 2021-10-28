package com.conlage.smartshopping.viewmodel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.model.data.local.db.entity.ProductList
import com.conlage.smartshopping.model.data.usecase.impl.*
import com.conlage.smartshopping.model.data.usecase.wrapper.UseCaseResult
import com.conlage.smartshopping.view.components.main.floating_button.FabStateEnum
import com.conlage.smartshopping.viewmodel.MainViewModel
import com.conlage.smartshopping.viewmodel.base.BaseViewModel
import com.conlage.smartshopping.viewmodel.events.DeleteEvent
import com.conlage.smartshopping.viewmodel.events.SaveEvent
import com.conlage.smartshopping.viewmodel.extension.containsId
import com.conlage.smartshopping.viewmodel.extension.getProductById
import com.conlage.smartshopping.viewmodel.state.MainScreenState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import dagger.assisted.Assisted
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


    private val deleteJobs: HashMap<Int, Job> = HashMap()

    //subscribe to data sources and observe the data
    init {
        subscribeOnDataSource(getProductList()) { list, state ->
            state.copy(
                productList = list as MutableList<Product>
            )
        }
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
        when(val result = productListUseCase.getProductList(query, currentValue.currentPage)){
            is UseCaseResult.Response<ProductList> -> result.value.list
            else -> mutableListOf()
        }


    //add current product to list and db
    override fun handlePlusButton(newProduct: Product) {
        viewModelScope.launch(dispatcherMain + errHandler) {
            currentValue.productList.add(newProduct)
            saveUseCase.saveProductInDb(newProduct)
        }
    }

    // inc quantity of product in current list and update db
    override fun handleIncProduct(productIndex: Int) {
        viewModelScope.launch(dispatcherMain + errHandler) {

            currentValue.productList[productIndex].quantity.inc()

            val product = currentValue.productList[productIndex]

            productUpdateUseCase.updateProductInDb(product)

        }
    }


    //delay 5000 and delete if true, if false -> stop delay and dont delete
    override fun handleProductCheckBox(productIndex: Int) {
        val product = currentValue.productList[productIndex]
        product.wantBeDeleted = !product.wantBeDeleted

        if(product.wantBeDeleted){
            val job = viewModelScope.launch(dispatcherMain + errHandler) {
                delay(5000)
                deleteUseCase.deleteProductFromDb(product)
                currentValue.productList.remove(product)
            }
            deleteJobs[productIndex] = job
        }else{
            val job = deleteJobs[productIndex] ?: return
            if(job.isActive) job.cancel()
        }
    }

    //handle search query and make request for product list, and update list
    override fun handleSearchQuery(query: String) {
        viewModelScope.launch(dispatcherMain + errHandler) {

            if(query.length < 3) currentValue.searchList.clear()

            updateState { it.copy(
                searchQuery = query,
                isLoadingSearchProducts = true
            ) }

            val list = getProductListFromNetwork(query)

            updateState { it.copy(
                isLoadingSearchProducts = false,
                searchList = list as MutableList<Product>
            ) }
        }
    }

    //change current state for closing/opening search
    override fun handleSearchOpen(isOpen: Boolean) {
        updateState { it.copy(
            isSearchOpen = isOpen
        ) }
    }

    override fun handleFabState() {
        if(currentValue.fabState == FabStateEnum.COLLAPSED){
            updateState { it.copy(
                fabState = FabStateEnum.EXPANDED
            ) }
        }else updateState{ it.copy(fabState = FabStateEnum.COLLAPSED) }
    }


    //dec quantity of product (if quantity == 0) -> delete item (handle delete product)
    override fun handleDecProduct(productIndex: Int) {
        viewModelScope.launch(dispatcherMain + errHandler) {

            if(currentValue.productList[productIndex].quantity.dec() <= 0){
                handleDecProduct(productIndex)
            }else{

                val product = currentValue.productList[productIndex]

                productUpdateUseCase.updateProductInDb(product)

            }

        }
    }

    override fun handleCameraPermission(isGranted: Boolean) {
        updateState { it.copy(isCameraGranted = isGranted) }
    }

    override fun handleStoragePermission(isGranted: Boolean) {
        updateState { it.copy(isStorageGranted = isGranted) }
    }


    override fun handleNewPage() {
        if(currentValue.searchQuery == null) return
        viewModelScope.launch(dispatcherMain + errHandler) {

            updateState { it.copy(currentPage = it.currentPage.inc()) }
            val newPageList = getProductListFromNetwork(currentValue.searchQuery!!)

            with(currentValue){
                searchList.addAll(searchList.size, newPageList)
            }

        }
    }

    override fun handleSaveProduct(productId: Int) {
        if(currentValue.productList.containsId(productId)) return
        viewModelScope.launch(dispatcherMain + errHandler) {
            val product = currentValue.searchList.getProductById(productId)

            saveUseCase.saveProductInDb(product)

            currentValue.productList.add(product)

            val index = currentValue.productList.indexOf(product)

            handleEvent(SaveEvent(index))
        }
    }

    override fun handleDeleteProductById(productId: Int) {
        if(!currentValue.productList.containsId(productId)) return
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
