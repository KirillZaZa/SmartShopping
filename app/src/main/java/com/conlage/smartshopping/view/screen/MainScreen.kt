package com.conlage.smartshopping.view.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
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
import com.conlage.smartshopping.view.components.main.add_item.AddItemCard
import com.conlage.smartshopping.view.components.main.fab.ButtonScanner
import com.conlage.smartshopping.view.components.main.added_list.list.AddedListProduct
import com.conlage.smartshopping.view.components.main.added_list.list.BulbDialog
import com.conlage.smartshopping.view.components.main.search.SearchProductComp
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

@ExperimentalComposeUiApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    vm: MainViewModelImpl,
    navController: NavController,
) {

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val screenState = remember { vm.state }
    val state = screenState.value

    val productListState = remember { vm.productListState }

    val addedListState =  rememberLazyListState()


    val context = LocalContext.current



    BackHandler() {
        focusManager.clearFocus(force = true)
    }




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
                searchQuery = state.searchQuery,
                isLoading = state.isLoadingSearchProducts,
                isSearchError = state.isSearchError,
                searchList = state.searchList,
                onQueryChange = { vm.handleSearchQuery(it) },
                onCloseClick = { vm.handleSearchOpen(isOpen = false) },
                onProductClick = {
                    val productId = state.searchList[it].id
                    navController
                        .navigate(
                            Screen.ProductScreen.withArgs(
                                "$productId",
                                "null",
                            )
                        )
                },
                focusRequester = focusRequester,
                focusManager = focusManager
            )

            Spacer(modifier = Modifier.height(24.dp))

            AddItemCard(
                onValueChange = { title->
                    vm.handleShopItemQuery(title)
                },
                value = state.addItemTitle,
                focusRequester = focusRequester,
                onDone = {
                    vm.handleNewShopItem()
                }
            )


            if (state.isLoadingProducts) {
                Spacer(modifier = Modifier.weight(0.5f))
                CircularProgressIndicator(color = Blue)
                Spacer(modifier = Modifier.weight(1f))
            } else {
                AddedListProduct(
                    productList = productListState,
                    addedListState = addedListState,
                    onLightClick = { shopItem ->
                        vm.handleDialogState(shopItem.title, state = true)
                    },
                    onCheckedChange = { index, isChecked ->
                        vm.handleProductCheckBox(index, isChecked)
                    }
                )
            }




        }

        if(state.isBulbOpen){
            BulbDialog(
                title = state.bulbTitle ,
                foundProducts = state.bulbList,
                isLoadingBulb = state.isLoadingBulb,
                onProductClick = {
                    val productId = state.bulbList[it].id
                    navController
                        .navigate(
                            Screen.ProductScreen.withArgs(
                                "$productId",
                                "null",
                            )
                        )
                },
                onDismissRequest = {
                    vm.handleDialogState("", state = false)
                }
            )
        }

        if (!state.isSearchOpen) {
            ButtonScanner(onClick = {

            })
        }


        /**
         * if isCameraGranted = false , and event is PermissionEvent
         * show snackbar for 3 sec
         *
         */

        //        SnackbarPermission()

    }


}




