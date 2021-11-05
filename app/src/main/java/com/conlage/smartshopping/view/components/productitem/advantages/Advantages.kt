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


@Composable
fun AdvantagesProduct(advantagesList: List<String>?) {
    val listState = rememberLazyListState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentHeight()
            .sizeIn(maxWidth = 172.dp)
            .background(Color.White, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp)
            .padding(top = 8.dp)
    ) {
        Text(
            text = "Достоинства",
            color = Color.White,
            modifier = Modifier
                .sizeIn(minWidth = 142.dp)
                .background(color = GreenColor, shape = RoundedCornerShape(30))
                .padding(vertical = 8.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center

        )

        Spacer(modifier = Modifier.height(16.dp))


        val advantages = advantagesList ?: listOf()
        advantages.forEach { advantage->
            Spacer(modifier = Modifier.height(4.dp))

            AdvantageItem(item = advantage, isAdvantage = true)

            Spacer(modifier = Modifier.height(4.dp))
        }

    }
}


@Composable
fun DisadvantagesProduct(disadvantagesList: List<String>?) {
    val listState = rememberLazyListState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentHeight()
            .sizeIn(maxWidth = 172.dp)
            .background(Color.White, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp)
            .padding(top = 8.dp)
    ) {

        Text(
            text = "Недостатки",
            color = Color.White,
            modifier = Modifier
                .sizeIn(minWidth = 142.dp)
                .background(color = RedColor, shape = RoundedCornerShape(30))
                .padding(vertical = 8.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        val advantages = disadvantagesList ?: listOf()
        advantages.forEach { advantage->
            Spacer(modifier = Modifier.height(4.dp))

            AdvantageItem(item = advantage, isAdvantage = false)

            Spacer(modifier = Modifier.height(4.dp))
        }


    }
}