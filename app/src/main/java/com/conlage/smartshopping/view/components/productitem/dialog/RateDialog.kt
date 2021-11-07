package com.conlage.smartshopping.view.components.productitem.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.conlage.smartshopping.R
import com.conlage.smartshopping.ui.theme.*


@Composable
fun RateDialog(
    rateDetails: Map<String, Double>?,
    onCloseRateDialog: () -> Unit
) {
    Dialog(
        onDismissRequest = onCloseRateDialog,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {

        val rateTitles = rateDetails?.map { it.key }
        val rateStars = rateDetails?.map { it.value }

        Box(contentAlignment = Alignment.BottomCenter) {
            Card(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .sizeIn(minHeight = 256.dp, maxHeight = 400.dp)
                    .padding(top = 64.dp)
                    .padding(bottom = 32.dp),
                backgroundColor = Color.White,
            ) {


                if (rateTitles == null && rateStars == null) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "Нет информации",
                            color = Standin,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                } else {

                    LazyColumn(modifier = Modifier.wrapContentSize()) {
                        itemsIndexed(rateTitles!!) { i, rateTitle ->
                            if (i == 0) {
                                Spacer(modifier = Modifier.height(24.dp))
                            }

                            RateItem(rateTitle = rateTitle, countOfStars = rateStars!![i])

                            if (i == rateTitles.size - 1) {
                                Spacer(modifier = Modifier.height(48.dp))
                            }
                        }
                    }
                }


            }

            TextButton(
                onClick = onCloseRateDialog,
            ) {
                Text(
                    text = "Закрыть",
                    color = Color.White,
                    modifier = Modifier
                        .background(color = Blue, shape = RoundedCornerShape(20.dp))
                        .padding(vertical = 16.dp)
                        .padding(horizontal = 64.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }


        }


    }
}

@Composable
private fun RateItem(rateTitle: String, countOfStars: Double) {
    Column(
        modifier =
        Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        Text(
            text = rateTitle,
            fontSize = 14.sp,
            color = DarkGray,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        GenerateStars(countOfStars = countOfStars.toInt())

    }
}

@Composable
private fun GenerateStars(countOfStars: Int) {

    val maxCount = 5

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        repeat(countOfStars) {
            // закрашенные
            Icon(
                painter = painterResource(id = R.drawable.ic_star_icon),
                contentDescription = "rate_icon",
                tint = Yellow,
                modifier = Modifier.size(20.dp)
            )
        }

        repeat(maxCount - countOfStars) {
            // незакрашенные
            Icon(
                painter = painterResource(id = R.drawable.ic_star_icon),
                contentDescription = "rate_icon",
                tint = Standin,
                modifier = Modifier.size(20.dp)
            )
        }

    }
}