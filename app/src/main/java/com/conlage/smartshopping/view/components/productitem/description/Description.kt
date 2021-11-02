package com.conlage.smartshopping.view.components.productitem.description

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.conlage.smartshopping.ui.theme.Blue
import com.conlage.smartshopping.ui.theme.DarkGray
import com.conlage.smartshopping.ui.theme.DividerColor
import com.conlage.smartshopping.ui.theme.Standin

@Composable
fun DescriptionProduct(
    description: String,
    isReadMore: Boolean,
    onClickReadMore: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
    ) {

        //header
        Text(
            text = "Описание",
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = DarkGray
        )
        
        Spacer(modifier = Modifier.height(12.dp))

        //description:  if false -> change maxLines to max, else -> maxLines = 5
        Text(
            text = description,
            overflow = TextOverflow.Ellipsis,
            color = Standin,
            maxLines = if(!isReadMore) 5 else Int.MAX_VALUE,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(8.dp))


        //button read more:  if false -> Читать далее, else -> скрыть
            Text(
                text = if(!isReadMore) "Читать далее" else "Скрыть",
                color = Blue,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                modifier = Modifier.clickable(onClick = onClickReadMore)

            )

        Spacer(modifier = Modifier.height(4.dp))


        Divider(color = DividerColor)

    }

}