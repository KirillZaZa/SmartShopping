package com.conlage.smartshopping.view.components.main.snackbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
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

@Composable
fun SnackbarPermission(
    onSettingsClick: () -> Unit
) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
            .background(Color.White, shape = RoundedCornerShape(20)),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Необходим доступ к камере",
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
        TextButton(
            onClick = onSettingsClick,
            modifier = Modifier
                .wrapContentSize()
                .padding(end = 8.dp)
        ) {
            Text(
                text = "Настройки",
                fontSize = 15.sp,
                color = Blue,
                fontWeight = FontWeight.Medium,

                )
        }
    }


}