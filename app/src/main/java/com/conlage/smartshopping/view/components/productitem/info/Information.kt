package com.conlage.smartshopping.view.components.productitem.info

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.conlage.smartshopping.model.data.network.dto.NetworkProduct
import com.conlage.smartshopping.R
import com.conlage.smartshopping.ui.theme.DarkGray
import com.conlage.smartshopping.ui.theme.Standin

@Composable
fun InformationProduct(details: NetworkProduct.Response.Details) {


    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter){
        Image(
            painter = painterResource(id = R.drawable.ic_info_icon),
            contentDescription = "info_icon",
            modifier = Modifier.offset(y = (-20).dp).zIndex(10f)
        )


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = Color.White, RoundedCornerShape(20.dp))
                .padding(horizontal = 12.dp)
                .padding(top = 4.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            //icon


            Spacer(modifier = Modifier.height(32.dp))

            // information

            repeat(6) { i ->
                when (i) {
                    0 -> {
                        InfoElement(placeHolder = "Производитель", item = details.manufacturer)
                    }
                    1 -> {
                        InfoElement(placeHolder = "Страна производства", item = details.countryOfManufacture)

                    }
                    2 -> {
                        InfoElement(placeHolder = "Изготовитель", item = details.producer)

                    }

                    3 -> {
                        InfoElement(placeHolder = "Состав", item = details.composition)

                    }

                    4 -> {
                        InfoElement(placeHolder = "Вес", item = details.weight)

                    }

                    5 -> {
                        InfoElement(placeHolder = "Год изготовления", item = details.yearOfResearch)

                    }


                }
            }


        }
    }


}

@Composable
private fun InfoElement(placeHolder: String, item: String) {

    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = "$placeHolder:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            color = Standin
        )

        Text(
            text = item,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = DarkGray
        )
    }


}



