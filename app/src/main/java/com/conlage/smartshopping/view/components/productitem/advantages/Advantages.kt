package com.conlage.smartshopping.view.components.productitem.advantages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.conlage.smartshopping.ui.theme.GreenColor
import com.conlage.smartshopping.ui.theme.RedColor
import com.conlage.smartshopping.ui.theme.Standin


@Composable
fun AdvantagesProduct(advantagesList: List<String>?) {
    val listState = rememberLazyListState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp)
            .padding(top = 8.dp)
    ) {
        Text(
            text = "Достоинства",
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = GreenColor, shape = RoundedCornerShape(30))
                .padding(vertical = 8.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center

        )

        Spacer(modifier = Modifier.height(16.dp))


        if (advantagesList.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Достоинств не обнаружено",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Standin,
                modifier = Modifier.wrapContentHeight(),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(32.dp))

        } else {
            advantagesList.forEach { advantage ->
                Spacer(modifier = Modifier.height(4.dp))

                AdvantageItem(item = advantage, isAdvantage = true)

                Spacer(modifier = Modifier.height(4.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

        }


    }
}


@Composable
fun DisadvantagesProduct(disadvantagesList: List<String>?) {
    val listState = rememberLazyListState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp)
            .padding(top = 8.dp)
    ) {

        Text(
            text = "Недостатки",
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = RedColor, shape = RoundedCornerShape(30))
                .padding(vertical = 8.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (disadvantagesList.isNullOrEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Недостатков не обнаружено",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Standin,
                modifier = Modifier.wrapContentHeight(),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(32.dp))

        } else {
            disadvantagesList.forEach { advantage ->
                Spacer(modifier = Modifier.height(4.dp))

                AdvantageItem(item = advantage, isAdvantage = false)

                Spacer(modifier = Modifier.height(4.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

        }


    }
}
