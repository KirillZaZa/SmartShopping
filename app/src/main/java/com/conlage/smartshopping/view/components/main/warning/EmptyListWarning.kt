package com.conlage.smartshopping.view.components.main.warning

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.conlage.smartshopping.R
import com.conlage.smartshopping.ui.theme.Gray


@Composable
fun EmptyListWarning() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()

    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_empty_list),
            contentDescription = "Список пуст"
        )
        Text(
            text = "Список пуст",
            color = Gray,
            fontSize = 24.sp
        )
        Text(
            text = "Нажмите + чтобы \n" +
                    "добавить товар",
            color = Gray,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light,
        )
    }
}