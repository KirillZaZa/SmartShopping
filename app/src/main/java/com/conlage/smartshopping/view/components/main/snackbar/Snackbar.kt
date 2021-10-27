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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.conlage.smartshopping.ui.theme.Blue
import com.conlage.smartshopping.ui.theme.DarkGray

@Preview(showBackground = true)
@Composable
fun SnackbarPermission() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .shadow(elevation = 10.dp, shape = RoundedCornerShape(20), clip = true)
            .background(Color.White, shape = RoundedCornerShape(20)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Для сохранения товаров необходим доступ к хранилищу",
            fontSize = 14.sp,
            color = DarkGray,
            fontWeight = FontWeight.Light,
            modifier = Modifier
                .wrapContentHeight()
                .padding(vertical = 16.dp)
                .padding(horizontal = 8.dp)
                .width(204.dp)
        )
        TextButton(onClick = {},
            modifier = Modifier
                .wrapContentSize()) {
            Text(
                text = "Настройки",
                fontSize = 16.sp,
                color = Blue,
                fontWeight = FontWeight.Medium,

            )
        }
    }

}