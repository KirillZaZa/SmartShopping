package com.conlage.smartshopping.viewmodel.impl

import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.model.data.usecase.impl.*
import com.conlage.smartshopping.viewmodel.MainViewModel
import com.conlage.smartshopping.viewmodel.base.BaseViewModel
import com.conlage.smartshopping.viewmodel.state.MainScreenState
import javax.inject.Inject

class MainViewModelImpl @Inject constructor(
    private val deleteUseCase: ProductDeleteUseCaseImpl,
    private val saveUseCase: ProductSaveUseCaseImpl,
    private val productListUseCase: ProductListUseCaseImpl,
    private val productIdUseCase: ProductIdUseCaseImpl,
    private val productBarCodeUseCase: ProductBarCodeUseCaseImpl
): BaseViewModel<MainScreenState>(MainScreenState()), MainViewModel {

    init {

    }

    override fun handlePlusButton() {

    }

    override fun handleIncProduct(product: Product) {
    }

    override fun handleOnProductClick(productItem: Int) {
    }

    override fun handleSearchQuery(query: String) {
    }

    override fun handleSearchOpen(isOpen: Boolean) {
    }

    override fun handleScanBarcode(barcode: String) {
    }

    override fun handleDeleteProduct(product: Product) {
    }


}
