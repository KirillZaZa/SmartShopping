package com.conlage.smartshopping.view.screen

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.conlage.smartshopping.ui.theme.BackgroundColor
import com.conlage.smartshopping.view.components.main.floating_button.ButtonNewProduct
import com.conlage.smartshopping.view.components.main.floating_button.FabItem
import com.conlage.smartshopping.view.components.main.snackbar.SnackbarPermission
import com.conlage.smartshopping.view.components.main.warning.EmptyListWarning
import com.conlage.smartshopping.view.navigation.Screen
import com.conlage.smartshopping.viewmodel.impl.MainViewModelImpl
import com.conlage.smartshopping.viewmodel.state.MainScreenState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


@Composable
fun TextMainHeader() {
    Text(
        text = "Список покупок",
        color = Color.DarkGray,
        fontSize = 26.sp,
        fontWeight = FontWeight.Medium,
    )
}

@ExperimentalPermissionsApi
@Preview
@Composable
//navController
//viewmodel
fun MainScreen(
    navController: NavController? = null,
) {

    val msc = MainScreenState()
    val context = LocalContext.current

    val scope = rememberCoroutineScope()




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

        if (msc.isSearchOpen) {

        }

        if (msc.productList.isNullOrEmpty()) {
            Spacer(modifier = Modifier.weight(1.0f))

            EmptyListWarning()

            Spacer(modifier = Modifier.weight(1.0f))
        } else {
            // product list
        }


        ButtonNewProduct(msc.fabState,
            onClick = {
                // vm handle fabState
            },
            onFabItemClick = {
                when (it) {
                    is FabItem.CameraFabItem ->{
                        if(msc.isCameraGranted){
                            navController!!
                                .navigate(Screen.ScannerScreen.route)
                        }
                    }

                    is FabItem.AddFabItem -> {
                        //handle isSearch = true
                    }
                }
            })


        /**
         * if isCameraGranted = false , and event is PermissionEvent
         * show snackbar for 3 sec
         *
         */

        SnackbarPermission()


    }

}


