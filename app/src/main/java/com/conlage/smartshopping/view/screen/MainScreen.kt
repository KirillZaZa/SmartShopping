package com.conlage.smartshopping.view.screen

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
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
import kotlinx.coroutines.launch


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


@ExperimentalAnimationApi
@ExperimentalPermissionsApi
@ExperimentalComposeUiApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    vm: MainViewModelImpl,
    navController: NavController,
    activity: Activity,
) {
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)


    val focusRequester = remember { FocusRequester() }

    val screenState = remember { vm.state }
    val state = screenState.value


    val productListState = remember { vm.productListState }


    val addedLazyListState =
        rememberLazyListState(
            state.addedProductListState.first,
            state.addedProductListState.second
        )

    val searchLazyListState = rememberLazyListState(
        state.searchListState.first,
        state.searchListState.second
    )

    val bulbLazyListState =
        rememberLazyListState(
            state.bulbListState.first,
            state.bulbListState.second
        )


    val context = LocalContext.current

    val scope = rememberCoroutineScope()





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
                searchList = screenState.value.searchList,
                onQueryChange = { vm.handleSearchQuery(it) },
                onCloseClick = { vm.handleSearchOpen(isOpen = false) },
                onProductClick = {
                    val productId = state.searchList[it].id

                    vm.handleSearchOpen(isOpen = true)

                    vm.handleListStates(
                        addedLazyListState.firstVisibleItemIndex to
                                addedLazyListState.firstVisibleItemScrollOffset,

                        searchLazyListState.firstVisibleItemIndex to
                                searchLazyListState.firstVisibleItemScrollOffset,

                        bulbLazyListState.firstVisibleItemIndex to
                                bulbLazyListState.firstVisibleItemScrollOffset
                    )

                    navController
                        .navigate(
                            Screen.ProductScreen.withArgs(
                                "$productId",
                                "null",
                                "false"
                            )
                        )
                },
                searchListState = searchLazyListState,

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
                },
                isFocused = {
                    vm.handleSearchOpen(!it)
                }
            )


            if (state.isLoadingProducts) {
                Spacer(modifier = Modifier.weight(1f))
                CircularProgressIndicator(color = Blue)
                Spacer(modifier = Modifier.weight(1f))
            } else {
                AddedListProduct(
                    productList = productListState,
                    addedListState = addedLazyListState,
                    onLightClick = { shopItem ->
                        vm.handleDialogState(shopItem.title, state = true)
                        vm.currentValue.currentBulbIndex = productListState.indexOf(shopItem)
                    },
                    onCheckedChange = { index, isChecked ->
                        vm.handleProductCheckBox(index, isChecked)
                    }
                )
            }


        }



        AnimatedVisibility(
            visible = state.isBulbOpen,
            exit = slideOutVertically(
                targetOffsetY = { 0 },
                animationSpec = tween(300, easing = FastOutSlowInEasing)
            )
        ) {
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
                                "true"
                            )
                        )
                    vm.handleBulbVisibility(false)
                    vm.handleListStates(
                        addedLazyListState.firstVisibleItemIndex to
                                addedLazyListState.firstVisibleItemScrollOffset,

                        searchLazyListState.firstVisibleItemIndex to
                                searchLazyListState.firstVisibleItemScrollOffset,

                        bulbLazyListState.firstVisibleItemIndex to
                                bulbLazyListState.firstVisibleItemScrollOffset
                    )
                },
                onDismissRequest = {
                    vm.handleBulbCheckbox(it)

                    vm.handleDialogState("", state = false)
                    scope.launch {
                        bulbLazyListState.scrollToItem(0, 0)
                    }

                },
                bulbListState = bulbLazyListState,
                isOpen = state.isBulbOpen
            )

        }



        ButtonScanner(
            onClick = {
                permissionState.launchPermissionRequest()
                when (permissionState.permission) {
                    Manifest.permission.CAMERA -> {
                        when {
                            permissionState.hasPermission -> {
                                vm.handleScannerButton()
                            }

                            permissionState.shouldShowRationale -> {

                                vm.handlePermission(false)

                            }

                            permissionState.isPermanentlyDenied() -> {

                                vm.handlePermission(true)
                            }
                        }
                    }
                }

            },
        )

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
                            barcode,
                            "false"
                        )
                    )
                }
            )
        }


        AnimatedVisibility(
            visible = state.isShouldShowRationale,
            enter = slideInVertically(
                animationSpec = tween(200, easing = FastOutSlowInEasing),
                initialOffsetY = { 1000 }
            ),
            exit = slideOutVertically(
                animationSpec = tween(300, easing = LinearEasing),
                targetOffsetY = { 1000 }
            )

        ) {
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




