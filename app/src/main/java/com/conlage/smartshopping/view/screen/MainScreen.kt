package com.conlage.smartshopping.view.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.conlage.smartshopping.ui.theme.BackgroundColor
import com.conlage.smartshopping.view.components.main.floating_button.ButtonNewProduct
import com.conlage.smartshopping.view.components.main.floating_button.FabItem
import com.conlage.smartshopping.view.components.main.search.SearchProductComp
import com.conlage.smartshopping.view.components.main.snackbar.SnackbarPermission
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

    val screenState = remember { vm.state }

    Log.e("Main", "MainScreen: $screenState")

    val context = LocalContext.current


//
//    if(args.id != null){
//        handleIdArg()
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(horizontal = 20.dp)
            .padding(top = 36.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        TextMainHeader()

        SearchProductComp(
            searchQuery = vm.currentValue.searchQuery,
            isLoading = vm.currentValue.isLoadingSearchProducts,
            searchList = vm.currentValue.searchList,
            onQueryChange = { vm.handleSearchQuery(it)},
            onCloseClick = { vm.handleSearchOpen(isOpen = false) },
            onProductClick = { /*TODO*/ },
            incClick = { /*TODO*/ },
            decClick = {}
        )
//
//        if (screenState.isSearchOpen) {
//
//        }

        if (screenState.value.productList.isNullOrEmpty()) {
            Spacer(modifier = Modifier.weight(0.5f))
            EmptyListWarning()
            Spacer(modifier = Modifier.weight(0.5f))
        } else {
            // product list
        }


        ButtonNewProduct(screenState.value.fabState,
            onClick = {
                // vm handle fabState
            },
            onFabItemClick = {
                when (it) {
                    is FabItem.CameraFabItem -> {
                        if (screenState.value.isCameraGranted) {
                            navController!!
                                .navigate(Screen.ScannerScreen.route)
                        } else {
                            // handle show snackbar
                        }
                    }

                    is FabItem.AddFabItem -> {
                        //handle isSearch = true
                        if (screenState.value.isStorageGranted) {
                            //open search
                        } else {
                            // handle show snackbar
                        }
                    }
                }
            })


        /**
         * if isCameraGranted = false , and event is PermissionEvent
         * show snackbar for 3 sec
         *
         */


//        SnackbarPermission()


    }

}




