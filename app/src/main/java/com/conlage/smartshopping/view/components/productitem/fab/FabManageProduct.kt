package com.conlage.smartshopping.view.components.productitem.fab

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.conlage.smartshopping.ui.theme.Blue
import com.conlage.smartshopping.ui.theme.RedColor

@Composable
fun FabManageProduct(onClick: () -> Unit, isAdded: Boolean) {


    Column(
        modifier = Modifier
            .padding(bottom = 16.dp)
    ) {

        var color = Blue

        if (isAdded) {
            color = RedColor
        }

        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            backgroundColor = color,
            shape = RoundedCornerShape(25)
        ) {
            if (!isAdded) {
                Text(
                    text = "Добавить продукт",
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            } else {
                Text(
                    text = "Удалить продукт",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

    }


}