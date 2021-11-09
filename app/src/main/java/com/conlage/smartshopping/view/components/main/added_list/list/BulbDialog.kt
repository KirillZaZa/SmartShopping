package com.conlage.smartshopping.view.components.main.added_list.list

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.conlage.smartshopping.R
import com.conlage.smartshopping.model.data.local.Product
import com.conlage.smartshopping.ui.theme.Blue
import com.conlage.smartshopping.ui.theme.DarkGray
import com.conlage.smartshopping.ui.theme.Orange
import com.conlage.smartshopping.ui.theme.Standin
import com.conlage.smartshopping.view.components.main.search.list.SearchList

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun BulbDialog(
    title: String,
    foundProducts: List<Product>,
    isLoadingBulb: Boolean,
    onProductClick: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true
        )
    ) {

        if (isLoadingBulb) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Blue)
            }
        } else if (foundProducts.isNullOrEmpty() && !isLoadingBulb) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Ничего не найдено",
                    color = Standin,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis
                )
            }
        } else {

            Column(
                modifier = Modifier
                    .sizeIn(minHeight = 256.dp, maxHeight = 400.dp)
                    .width(width = 350.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 4.dp)
            ) {

                Spacer(modifier = Modifier.height(12.dp))

                BulbContent(
                    title = title,
                    onDismissRequest = onDismissRequest,
                    foundProducts = foundProducts,
                    onProductClick = onProductClick
                )

            }
        }

    }


}


@ExperimentalMaterialApi
@Composable
private fun BulbContent(
    title: String,
    onDismissRequest: () -> Unit,
    foundProducts: List<Product>,
    onProductClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            enabled = false,
            checked = false,
            onCheckedChange = {},
            modifier = Modifier.padding(start = 6.dp),
            colors = CheckboxDefaults.colors(
                disabledColor = Standin
            )
        )

        Spacer(modifier = Modifier.width(20.dp))

        Text(
            text = title,
            color = DarkGray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.weight(1f))


        IconButton(
            onClick = onDismissRequest,
            modifier = Modifier
                .size(30.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close_icon),
                contentDescription = null,
                tint = Color.LightGray
            )
        }


    }
    SearchList(
        searchList = foundProducts,
        onProductClick = onProductClick
    )
}
