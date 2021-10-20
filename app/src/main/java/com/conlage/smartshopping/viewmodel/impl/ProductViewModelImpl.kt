package com.conlage.smartshopping.viewmodel.impl

import com.conlage.smartshopping.model.data.usecase.impl.*
import com.conlage.smartshopping.viewmodel.ProductViewModel
import com.conlage.smartshopping.viewmodel.base.BaseViewModel
import com.conlage.smartshopping.viewmodel.state.ProductScreenState
import javax.inject.Inject

class ProductViewModelImpl @Inject constructor(
    private val deleteUseCase: ProductDeleteUseCaseImpl,
    private val saveUseCase: ProductSaveUseCaseImpl,
): BaseViewModel<ProductScreenState>(ProductScreenState()), ProductViewModel{

    init {

    }

    override fun handleAddButton() {

    }

    override fun handleDeleteButton() {
    }

    override fun handleReadMore() {
    }

    override fun handleBackButton() {
    }

}