package com.conlage.smartshopping.view.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.conlage.smartshopping.ui.theme.BackgroundColor
import com.conlage.smartshopping.ui.theme.Blue
import com.conlage.smartshopping.view.components.main.fab.ButtonScanner
import com.conlage.smartshopping.view.components.main.list.added.AddedListProduct
import com.conlage.smartshopping.view.components.main.search.SearchProductComp
import com.conlage.smartshopping.view.components.main.warning.EmptyListWarning
import com.conlage.smartshopping.view.navigation.Screen
import com.conlage.smartshopping.viewmodel.impl.MainViewModelImpl


@Composable
fun TextMainHeader() {
    Text(
        text = "Список покупок",
        color = Color.DarkGray,
        fontSize = 26.sp,
        fontWeight = FontWeight.Medium,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    vm: MainViewModelImpl,
    navController: NavController,
) {

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val screenState = remember { vm.state }
    val screen = screenState.value

    val productListState = remember { vm.productListState }


    val context = LocalContext.current



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(horizontal = 20.dp)
            .padding(top = 36.dp),
        contentAlignment = Alignment.BottomEnd
    ) {


        Column() {

            TextMainHeader()

            SearchProductComp(
                searchQuery = screen.searchQuery,
                isLoading = screen.isLoadingSearchProducts,
                isSearchError = screen.isSearchError,
                searchList = screen.searchList,
                onQueryChange = { vm.handleSearchQuery(it) },
                onCloseClick = { vm.handleSearchOpen(isOpen = false) },
                onProductClick = {
                    val productId = screen.searchList[it].id
                    navController
                        .navigate(
                            Screen.ProductScreen.withArgs(
                                "$productId",
                                "null",
                            )
                        ){
                        }
                },
                incClick = {
                    vm.handleIncSearchItem(it) { product ->
                        vm.handleIncAddedProduct(product)
                    }
                },
                decClick = {
                    vm.handleDecSearchItem(it) { product ->
                        vm.handleDecAddedProduct(product)
                    }
                },
                onAddTextClick = {

                },
                focusRequester = focusRequester,
                focusManager = focusManager
            )



            if (productListState.isNullOrEmpty() && !screen.isLoadingProducts) {
                Spacer(modifier = Modifier.weight(0.5f))
                EmptyListWarning()
                Spacer(modifier = Modifier.weight(1f))
            } else if (screen.isLoadingProducts) {
                Spacer(modifier = Modifier.weight(0.5f))
                CircularProgressIndicator(color = Blue)
                Spacer(modifier = Modifier.weight(1f))
            } else {

                AddedListProduct(
                    productList = productListState,
                    onProductClick = { product ->
                        navController
                            .navigate(
                                Screen.ProductScreen.withArgs(
                                    "${product.id}",
                                    "null",
                                )
                            )
                    }
                )
            }


        }
        if (!screen.isSearchOpen) {
            ButtonScanner(onClick = {})
        }

        /**
         * if isCameraGranted = false , and event is PermissionEvent
         * show snackbar for 3 sec
         *
         */

        //        SnackbarPermission()

    }


}




