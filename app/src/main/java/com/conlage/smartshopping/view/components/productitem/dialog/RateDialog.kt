package com.conlage.smartshopping.view.components.productitem.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.conlage.smartshopping.ui.theme.Yellow
import com.conlage.smartshopping.R
import com.conlage.smartshopping.ui.theme.DarkGray
import com.conlage.smartshopping.ui.theme.Standin


@Composable
fun RateDialog(
    rateDetails: Map<String, Int>,
    onCloseRateDialog: () -> Unit
) {
    Dialog(
        onDismissRequest = onCloseRateDialog,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        val rateTitles = rateDetails.map { it.key }
        val rateStars = rateDetails.map { it.value }

        Card(
            elevation = 4.dp,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .size(400.dp)
                .padding(horizontal = 12.dp)
                .padding(top = 12.dp)
                .padding(bottom = 32.dp),
            backgroundColor = Color.White,

            ) {

            LazyColumn(modifier = Modifier.wrapContentSize()) {
                itemsIndexed(rateTitles) { i, rateTitle ->
                    RateItem(rateTitle = rateTitle, countOfStars = rateStars[i])
                }
            }

        }

    }
}

@Composable
private fun RateItem(rateTitle: String, countOfStars: Int) {
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
            fontSize = 16.sp,
            color = DarkGray,
            fontWeight = FontWeight.Medium
        )

        GenerateStars(countOfStars = countOfStars)

    }
}

@Composable
private fun GenerateStars(countOfStars: Int) {

    val maxCount = 5

    Row(
        modifier = Modifier.fillMaxWidth(),
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