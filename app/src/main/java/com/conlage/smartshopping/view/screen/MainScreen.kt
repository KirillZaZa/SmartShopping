package com.conlage.smartshopping.view.screen

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.conlage.smartshopping.ui.theme.BackgroundColor
import com.conlage.smartshopping.ui.theme.Blue
import com.conlage.smartshopping.view.components.main.add_item.AddItemCard
import com.conlage.smartshopping.view.components.main.fab.ButtonScanner
import com.conlage.smartshopping.view.components.main.added_list.list.AddedListProduct
import com.conlage.smartshopping.view.components.main.added_list.list.BulbDialog
import com.conlage.smartshopping.view.components.main.permission.isPermanentlyDenied
import com.conlage.smartshopping.view.components.main.search.SearchProductComp
import com.conlage.smartshopping.view.components.main.snackbar.SnackbarPermission
import com.conlage.smartshopping.view.components.scanner.DialogScanner
import com.conlage.smartshopping.view.navigation.Screen
import com.conlage.smartshopping.viewmodel.impl.MainViewModelImpl
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState


@Composable
fun TextMainHeader() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Список покупок",
        color = Color.DarkGray,
        fontSize = 26.sp,
        fontWeight = FontWeight.Medium,
        textAlign = TextAlign.Start
    )
}

@ExperimentalPermissionsApi
@ExperimentalComposeUiApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    vm: MainViewModelImpl,
    navController: NavController,
    activity: Activity
) {

    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val screenState = remember { vm.state }
    val state = screenState.value

    val productListState = remember { vm.productListState }

    val addedListState = rememberLazyListState()


    val context = LocalContext.current





    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(horizontal = 20.dp)
            .padding(top = 72.dp),
        contentAlignment = Alignment.BottomEnd
    ) {


        Column(horizontalAlignment = Alignment.CenterHorizontally) {

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
                focusManager = focusManager,
                onFocusChanged = { vm.handleSearchOpen(isOpen = false) }
            )

            Spacer(modifier = Modifier.height(24.dp))

            AddItemCard(
                onValueChange = { title ->
                    vm.handleShopItemQuery(title)
                },
                value = state.addItemTitle,
                focusRequester = focusRequester,
                onDone = {
                    vm.handleNewShopItem()
                }
            )


            if (state.isLoadingProducts) {
                Spacer(modifier = Modifier.weight(1f))
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

        if (state.isBulbOpen) {
            BulbDialog(
                title = state.bulbTitle,
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
            ButtonScanner(
                onClick = {
                    //handle permissions
                    permissionState.launchPermissionRequest()


                    when (permissionState.permission) {
                        Manifest.permission.CAMERA -> {
                            when {
                                permissionState.hasPermission -> {
                                    vm.handleScannerButton()
                                }

                                permissionState.shouldShowRationale ->{
                                    vm.handlePermissionCount()
                                }

                                permissionState.isPermanentlyDenied() -> {

                                    vm.handlePermissionCount()

                                }
                            }
                        }
                    }

                })
        }

        if (state.isScannerOpen) {
            DialogScanner(
                activity = activity,

                onDismissRequest = {
                    vm.handleScannerButton()
                },
                onBarcodeResult = { barcode ->
                    navController.navigate(
                        Screen.ProductScreen.withArgs(
                            "0",
                            barcode
                        )
                    )
                }
            )
        }


        /**
         * if isCameraGranted = false , and event is PermissionEvent
         * show snackbar for 3 sec
         *
         */
        if (state.isShouldShowRationale) {
            SnackbarPermission(onSettingsClick = {
                context.startActivity(
                    Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", context.packageName, null)
                    )
                )
            })
        }

    }


}




