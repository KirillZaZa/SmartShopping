package com.conlage.smartshopping.view.components.main.added_list.list

import android.widget.Space
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.window.SecureFlagPolicy
import com.conlage.smartshopping.R
import com.conlage.smartshopping.model.data.local.Product
import com.conlage.smartshopping.ui.theme.Blue
import com.conlage.smartshopping.ui.theme.DarkGray
import com.conlage.smartshopping.ui.theme.Orange
import com.conlage.smartshopping.ui.theme.Standin
import com.conlage.smartshopping.view.components.main.search.list.SearchItem
import com.conlage.smartshopping.view.components.main.search.list.SearchList
import com.google.gson.annotations.Until

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
fun BulbDialog(
    title: String,
    foundProducts: List<Product>,
    isLoadingBulb: Boolean,
    bulbListState: LazyListState,
    onProductClick: (Int) -> Unit,
    onDismissRequest: (Boolean) -> Unit,
    isOpen: Boolean
) {
    var isChecked by remember {
        mutableStateOf(false)
    }
    Dialog(
        onDismissRequest = { onDismissRequest(isChecked)},
        properties = DialogProperties(
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false,
            dismissOnBackPress = true,

            )
    ) {


        if (isLoadingBulb) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Blue)
            }
        } else if (!isLoadingBulb && isOpen && foundProducts.isEmpty()) {
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
        }

        AnimatedVisibility(
            foundProducts.isNotEmpty(), enter = fadeIn(
                animationSpec = tween(400, easing = FastOutSlowInEasing)
            )
        ) {

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height(400.dp)
                    .width(400.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(20.dp))
                    .padding(bottom = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                BulbContent(
                    title = title,
                    onDismissRequest = onDismissRequest,
                    foundProducts = foundProducts,
                    onProductClick = onProductClick,
                    bulbListState = bulbListState,
                    onCheckedChange = {
                        isChecked = !isChecked
                    },
                    isChecked = isChecked
                )


            }

        }

    }
}


@ExperimentalMaterialApi
@Composable
private fun BulbContent(
    title: String,
    onDismissRequest: (Boolean) -> Unit,
    foundProducts: List<Product>,
    bulbListState: LazyListState,
    onProductClick: (Int) -> Unit,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            enabled = true,
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                uncheckedColor = Standin,
                checkmarkColor = Color.White,
                checkedColor = Blue
            )
        )


        Text(
            text = title,
            color = DarkGray,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.offset(x = (-4).dp)
        )

        Spacer(modifier = Modifier.weight(1f))


        IconButton(
            onClick = {onDismissRequest(isChecked)},
            modifier = Modifier
                .size(30.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close_icon),
                contentDescription = null,
                tint = Color.LightGray,
            )
        }
        
        Spacer(modifier = Modifier.width(8.dp))

    }
    Spacer(modifier = Modifier.height(4.dp))


    LazyColumn(
        state = bulbListState,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .wrapContentSize()
    ) {
        itemsIndexed(foundProducts) { i, product ->
            val isLast = i == foundProducts.size - 1
            SearchItem(
                product = product,
                onProductClick = { onProductClick(i) },
                isLast = isLast
            )
        }

    }

    Spacer(modifier = Modifier.height(24.dp))

}
