package com.conlage.smartshopping.view.screen

import android.graphics.BitmapFactory
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.conlage.smartshopping.model.data.local.ProductDetails
import com.conlage.smartshopping.model.data.network.dto.NetworkProduct
import com.conlage.smartshopping.ui.theme.BackgroundColor
import com.conlage.smartshopping.view.components.productitem.fab.FabManageProduct
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


@Composable
fun TextProductHeader(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
                contentDescription = "back_button",
                tint = DarkGray,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.weight(0.5f))
        Text(
            text = title,
            maxLines = 2,
            fontSize = 17.sp,
            color = DarkGray,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(200.dp),
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(1f))

    }
}


//viewmodel
//navController
@Composable
fun ProductScreen(
    navController: NavController,
    vm: ProductViewModelImpl,
    close: () -> Unit
) {
    val stateRemember = vm.state
    val state = stateRemember.value

    val scrollState = rememberScrollState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(state = scrollState)
                .fillMaxSize()
                .padding(top = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (state.isLoading && state.productDetails == null) {

                CircularProgressIndicator(color = Blue)
                Spacer(modifier = Modifier.weight(1f))

            } else if (state.productDetails == null && !state.isLoading) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Не удалось получить продукт",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Standin,
                )
                Spacer(modifier = Modifier.weight(1f))


            } else {
                TextProductHeader(title = state.productDetails!!.title) {
                    //onclick back
                }

                Spacer(modifier = Modifier.height(32.dp))

                AboutProduct(
                    bitmap = state.productDetails!!.bitmap!!,
                    rate = state.productDetails!!.rate,
                    price = state.productDetails!!.price,
                    onAboutRateClick = { /*vm handle isRateOpen = !isRateOpen*/ },
                    onResearchingClick = {}
                )

                Spacer(modifier = Modifier.height(32.dp))

                DescriptionProduct(
                    description = state.productDetails!!.description,
                    //vm.isReadMore
                    isReadMore = false,
                    onClickReadMore = {
                        // vm.handleClickReadMore
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))


//            Advantages

                Row(horizontalArrangement = Arrangement.SpaceBetween) {

                    //passing the advantages list
                    AdvantagesProduct(advantagesList = state.productDetails!!.advantages)

                    Spacer(modifier = Modifier.weight(1f))

                    //passing the disadvantages list
                    DisadvantagesProduct(disadvantagesList = state.productDetails!!.disadvantages)
                }

                //Information

                Spacer(modifier = Modifier.height(24.dp))

                InformationProduct(details = state.productDetails!!.details)

                Spacer(modifier = Modifier.height(20.dp))

                //barcode
                Barcode(bitmapBarcode = state.productDetails!!.barcodeImg)


                Spacer(modifier = Modifier.height(128.dp))

                //rate dialog if isRateOpen = true
                if (false) {
                    RateDialog(rateDetails = state.productDetails!!.rate_details) {
                        //vm isRate = false
                    }
                }
            }

            BackHandler() {
                navController.navigate(Screen.MainScreen.route) {
                    popUpTo(Screen.ProductScreen.route) {
                        inclusive = true
                    }
                }
                close()
            }

        }
        if (!state.isLoading || state.productDetails != null) {
            FabManageProduct(onClick = { /*TODO*/ }, isAdded = state.isAdded)

        }
    }

}