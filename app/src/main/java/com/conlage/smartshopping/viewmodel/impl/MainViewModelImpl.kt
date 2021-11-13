package com.conlage.smartshopping.viewmodel.impl

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
import kotlin.collections.HashMap


class MainViewModelImpl @Inject constructor(
    private val deleteUseCase: ProductDeleteUseCaseImpl,
    private val saveUseCase: ProductSaveUseCaseImpl,
    private val productListUseCase: ProductListUseCaseImpl,
) : BaseViewModel<MainScreenState>(MainScreenState()), MainViewModel {

    var productListState: MutableList<ShopItem> = mutableStateListOf()

    private val searchJobs: HashMap<Int, Job> = HashMap()
    private val deleteJobs: HashMap<String, Job> = HashMap()

    //subscribe to data sources and observe the data
    init {
        getProductList()
    }

    fun handleBulbVisibility(isVisible: Boolean) {

        if (isVisible) {

            viewModelScope.launch(dispatcherMain + errHandler) {

                delay(350)
                updateState {
                    it.copy(
                        isBulbOpen = isVisible
                    )
                }

            }

        } else {

            updateState {
                it.copy(isBulbOpen = false, searchList = it.searchList)
            }
        }
    }


    override fun handleDialogState(shopItemTitle: String, state: Boolean) {
        if (state) {

            viewModelScope.launch(dispatcherMain + errHandler) {
                if (shopItemTitle.length > 2) {
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
                } else updateState {
                    it.copy(
                        isBulbOpen = true,
                        isLoadingBulb = false,
                        bulbList = mutableListOf()
                    )
                }


            }
        } else {

            updateState {
                it.copy(
                    isBulbOpen = false,
                    isLoadingBulb = false,
                    bulbList = mutableListOf()
                )
            }

        }

    }


    override fun handleScannerButton() {
        updateState {
            it.copy(
                isScannerOpen = !it.isScannerOpen,
            )
        }
    }


    override fun handleShopItemQuery(title: String) {

        updateState { it.copy(addItemTitle = title) }

    }


    override fun handlePermission(denied: Boolean) {
        if (denied) {
            handleSnackbarPermission()
        }

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


    override fun handleProductCheckBox(shopItemIndex: Int, wantBeDeleted: Boolean) {
        val product = productListState[shopItemIndex]
        product.wantBeDeleted = !product.wantBeDeleted

        productListState.removeAt(shopItemIndex)
        productListState.add(shopItemIndex, product)


        if (product.wantBeDeleted) {
            val job = viewModelScope.launch(dispatcherMain + errHandler) {
                delay(2000)

                product.isItemDisappear = true
                productListState.add(shopItemIndex, product)
                productListState.remove(product)

                deleteUseCase.deleteProductFromDb(product)
            }
            deleteJobs[product.id] = job
        } else {
            val job = deleteJobs[product.id] ?: return
            if (job.isActive) job.cancel()
        }
    }

    override fun handleNewShopItem() {
        if (currentValue.addItemTitle.isBlank()) return

        val shopItem = ShopItem(
            title = currentValue.addItemTitle,
        )

        updateState {
            it.copy(
                addItemTitle = ""
            )
        }

        viewModelScope.launch(dispatcherMain + errHandler) {
            val list = productListState.toMutableList()

            productListState.clear()
            delay(50)
            productListState.add(0, shopItem)
            delay(50)
            productListState.addAll(list)
        }

        viewModelScope.launch(dispatcherMain + errHandler) {
            saveUseCase.saveProductInDb(shopItem)
        }
    }


    override fun getProductList() {
        viewModelScope.launch(dispatcherMain + errHandler) {
            currentValue.isLoadingProducts = true


            val list = when (val result = productListUseCase.getProductListFromDb()) {
                is UseCaseResult.Response<List<ShopItem>> -> result.value
                else -> mutableListOf()
            }
            updateState {
                it.copy(
                    isLoadingProducts = false
                )
            }

            productListState.addAll(list)


        }

    }

    override suspend fun getProductListFromNetwork(query: String): List<Product> =
        when (val result = productListUseCase.getProductList(query, currentValue.currentPage)) {

            is UseCaseResult.Response<ProductList> -> {

                result.value.list
            }
            else -> mutableListOf()
        }


    override fun handleSearchQuery(query: String) {
        searchJobs.forEach {
            it.value.cancel()
        }

        updateState {
            it.copy(
                searchQuery = query,
                isSearchOpen = true,
                isLoadingSearchProducts = true,
                searchListState = 0 to 0
            )
        }



        if (query.length > 2) {


            val job = viewModelScope.launch(dispatcherMain + errHandler) {
                delay(200)

                currentValue.searchList.clear()

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

            updateState {
                it.copy(
                    isSearchOpen = true,
                    searchQuery = "",
                    isLoadingSearchProducts = false,
                    isSearchError = false,
                    searchList = mutableListOf(),
                    searchListState = 0 to 0
                )
            }
        } else {
            updateState {
                it.copy(
                    isSearchOpen = false,
                    searchQuery = it.searchQuery,
                    isLoadingSearchProducts = false,
                    isSearchError = false
                )
            }
        }


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

    override fun handleBulbCheckbox(isChecked: Boolean) {
        if (!isChecked) return
        else {
            handleProductCheckBox(currentValue.currentBulbIndex, isChecked)

        }
    }

    override fun handleListStates(vararg state: Pair<Int, Int>?) {

        updateState {
            it.copy(
                addedProductListState = state.component1() ?: 0 to 0,
                searchListState = state.component2() ?: 0 to 0,
                bulbListState = state.component3() ?: 0 to 0
            )
        }
    }


    class MainViewModelFactory @AssistedInject constructor(
        private val deleteUseCase: ProductDeleteUseCaseImpl,
        private val saveUseCase: ProductSaveUseCaseImpl,
        private val productListUseCase: ProductListUseCaseImpl,
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModelImpl::class.java))
                return MainViewModelImpl(
                    deleteUseCase,
                    saveUseCase,
                    productListUseCase,
                ) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }

        @AssistedFactory
        interface Factory {

            fun create(): MainViewModelFactory

        }

    }

}
