package com.conlage.smartshopping.view.components.productitem.barcode

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.conlage.smartshopping.ui.theme.DarkGray
import com.conlage.smartshopping.ui.theme.Standin


@Composable
fun Barcode(bitmapBarcode: Bitmap?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp)
            .padding(vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        if (bitmapBarcode == null) {
            Text(
                text = "Штрих-код отсутствует",
                fontSize = 16.sp,
                color = Standin,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        } else {
            Image(
                bitmap = bitmapBarcode.asImageBitmap(),
                contentDescription = "barcode_image",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}