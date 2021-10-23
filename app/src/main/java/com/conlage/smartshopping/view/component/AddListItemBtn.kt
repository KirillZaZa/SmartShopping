package com.conlage.smartshopping.view.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.conlage.smartshopping.ui.theme.Purple

@Preview
@Composable
fun AddListItemBtn(onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.CenterEnd,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Purple,
                contentColor = Color.White
            ),
            modifier = Modifier
                .width(48.dp)
                .height(48.dp),
            shape = RoundedCornerShape(25)
        ) {
            Text(
                text = "+",
                fontSize = 26.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}