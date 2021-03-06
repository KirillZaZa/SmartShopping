package com.conlage.smartshopping.view.screen

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.conlage.smartshopping.ui.theme.BackgroundColor
import com.conlage.smartshopping.R
import com.conlage.smartshopping.ui.theme.Blue
import com.conlage.smartshopping.ui.theme.DarkGray
import com.conlage.smartshopping.ui.theme.Standin
import com.conlage.smartshopping.view.components.productitem.about.AboutProduct
import com.conlage.smartshopping.view.components.productitem.advantages.AdvantagesProduct
import com.conlage.smartshopping.view.components.productitem.advantages.DisadvantagesProduct
import com.conlage.smartshopping.view.components.productitem.barcode.Barcode
import com.conlage.smartshopping.view.components.productitem.description.DescriptionProduct
import com.conlage.smartshopping.view.components.productitem.dialog.RateDialog
import com.conlage.smartshopping.view.components.productitem.info.InformationProduct
import com.conlage.smartshopping.view.navigation.Screen
import com.conlage.smartshopping.viewmodel.impl.ProductViewModelImpl
import kotlinx.coroutines.launch


@Composable
fun TextProductHeader(title: String, onClick: () -> Unit) {
//    val newTitle = if (title.length > 24) {
//        title.substring(0, 20) + "..."
//    } else title
    val newTitle = title
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = BackgroundColor)
            .padding(top = 36.dp)
            .padding(horizontal = 30.dp)
            .zIndex(11f),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
            contentDescription = "back_button",
            tint = DarkGray,
            modifier = Modifier
                .size(32.dp)
                .clickable(onClick = onClick)
                .offset(x = (-16).dp)
        )

        Text(
            text = newTitle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            color = DarkGray,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start,
            modifier = Modifier.offset(x = (-16).dp).padding(horizontal = 8.dp)

        )
    }
}


private fun navigateBack(
    navController: NavController,
) {

    navController.navigate(Screen.MainScreen.route) {
        popUpTo(Screen.MainScreen.route) {
            inclusive = true
        }
        this.launchSingleTop = true
    }
}


@ExperimentalComposeUiApi
@ExperimentalUnitApi
@Composable
fun ProductScreen(
    navController: NavController,
    vm: ProductViewModelImpl,
    close: (Boolean) -> Unit,
    isVisible: Boolean
) {
    val stateRemember = vm.state
    val state = stateRemember.value

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    val context = LocalContext.current


    BackHandler {
        navigateBack(navController = navController)
        close(isVisible)
    }



    if (state.isLoading && state.productDetails == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor), contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Blue, modifier = Modifier.size(72.dp))
        }

    } else if (state.productDetails == null && !state.isLoading && !state.isClosing) {


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "???? ?????????????? ???????????????? ??????????????",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Standin,
            )
        }


    } else if (state.isClosing) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
        )
    } else if (state.productDetails != null) {
        TextProductHeader(title = state.productDetails!!.title) {
            navigateBack(navController = navController)
            close(isVisible)
        }
        Box(
            modifier = Modifier
                .verticalScroll(state = scrollState)
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(top = 72.dp)
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.TopCenter
        ) {


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {


                Spacer(modifier = Modifier.height(24.dp))

                AboutProduct(
                    bitmap = state.productDetails!!.bitmap,
                    rate = state.productDetails!!.rate,
                    price = state.productDetails!!.price,
                    onAboutRateClick = { vm.handleAboutEvaluation() },
                    onResearchingClick = {

                        if (state.productDetails!!.research_document != null) {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(state.productDetails!!.research_document)
                            )
                            context.startActivity(intent)
                        }


                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                DescriptionProduct(
                    description = state.productDetails!!.description,
                    isReadMore = state.isReadMore,
                    onClickReadMore = {
                        if (state.isReadMore) {
                            scope.launch {
                                scrollState.scrollTo((scrollState.value / 3))
                            }
                        }
                        vm.handleReadMore()

                    }
                )

                Spacer(modifier = Modifier.height(24.dp))



                AdvantagesProduct(advantagesList = state.productDetails!!.advantages)

                Spacer(modifier = Modifier.height(24.dp))

                DisadvantagesProduct(disadvantagesList = state.productDetails!!.disadvantages)

                Spacer(modifier = Modifier.height(32.dp))


                InformationProduct(details = state.productDetails!!.details)

                Spacer(modifier = Modifier.height(20.dp))

                Barcode(bitmapBarcode = state.productDetails!!.barcodeImg)


                Spacer(modifier = Modifier.height(40.dp))

                if (state.isEvaluation) {
                    RateDialog(
                        rateDetails = state.productDetails!!.rate_details,
                        onCloseRateDialog = { vm.handleAboutEvaluation() }
                    )
                }
            }

        }

    }

}



