package com.conlage.smartshopping.view.components.main.added_list.list

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.conlage.smartshopping.R
import com.conlage.smartshopping.model.data.local.db.entity.ShopItem
import com.conlage.smartshopping.ui.theme.Blue
import com.conlage.smartshopping.ui.theme.DarkGray
import com.conlage.smartshopping.ui.theme.Orange

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun AddedProduct(
    shopItem: ShopItem,
    onLightClick: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    productIndex: Int,
    listSize: Int,
) {
    val scaleOn = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    LaunchedEffect(key1 = shopItem.id) {
        if (!shopItem.wantBeDeleted)
            scaleOn.animateTo(1f)
    }



    val modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)
        .scale(scaleOn.value)
    if (productIndex == 0) {
        Spacer(modifier = Modifier.height(24.dp))
    }

    AnimatedVisibility(
        visible = !shopItem.isItemDisappear,
        exit = fadeOut(
            animationSpec = tween(
                300,
                delayMillis = 1700, easing = FastOutSlowInEasing
            )
        )
    ) {
        Card(
            backgroundColor = Color.White,
            modifier = modifier,
            shape = RoundedCornerShape(30),
            elevation = 0.dp
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = shopItem.wantBeDeleted,
                    onCheckedChange = onCheckedChange,
                    colors = CheckboxDefaults.colors(
                        uncheckedColor = Blue,
                        checkedColor = Blue,
                        checkmarkColor = Color.White
                    ),
                    modifier = Modifier
                        .padding(start = 4.dp)

                )

                Text(
                    text = shopItem.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = DarkGray,
                    modifier = Modifier
                        .sizeIn(maxWidth = 216.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = onLightClick,
                    modifier = Modifier
                        .size(width = 64.dp, height = 32.dp)
                        .background(color = Orange, shape = RoundedCornerShape(30))
                        .padding(vertical = 2.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_bulb_icon),
                        contentDescription = "bulb_icon",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.padding(end = 8.dp))
            }


        }

    }


    if (productIndex == listSize - 1) {
        Spacer(modifier = Modifier.height(96.dp))
    }

}