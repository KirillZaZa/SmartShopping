package com.conlage.smartshopping.view.components.main.snackbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.conlage.smartshopping.ui.theme.Blue
import com.conlage.smartshopping.ui.theme.DarkGray
import com.conlage.smartshopping.ui.theme.Gray

@Preview(showBackground = true)
@Composable
fun SnackbarPermission() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(20), clip = true)
            .background(Color.White, shape = RoundedCornerShape(20)),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Для сохранения товаров необходим доступ к хранилищу",
            fontSize = 14.sp,
            color = Gray,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .wrapContentHeight()
                .padding(vertical = 16.dp)
                .padding(start = 20.dp)
                .width(200.dp),
            textAlign = TextAlign.Start
        )
        TextButton(onClick = {},
            modifier = Modifier
                .wrapContentSize()
                .padding(end = 8.dp)) {
            Text(
                text = "Настройки",
                fontSize = 15.sp,
                color = Blue,
                fontWeight = FontWeight.Medium,

            )
        }
    }

}