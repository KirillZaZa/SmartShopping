package com.conlage.smartshopping.view.components.main.search_list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.ui.theme.Blue

/**
 *
 *@param onChangedText: (String) -> Unit
 *
 *
 */
@Preview
@Composable
fun SearchProductComp() {

    val query = "Молоко"

    val product = Product(
        price = "45.38",
        image = "",
        rate = 4.55,
        title = "Молоко Parmalat",
        id = 3213
    )

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .padding(20.dp)
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(20)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //search edit
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            OutlinedTextField(
                value = query,
                onValueChange = {},
                maxLines = 1,
                modifier = Modifier.background(color = Color.Transparent)
            )
        }
    }
}