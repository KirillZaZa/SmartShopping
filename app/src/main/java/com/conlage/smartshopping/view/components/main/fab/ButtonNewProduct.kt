package com.conlage.smartshopping.view.components.main.fab

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.conlage.smartshopping.R
import com.conlage.smartshopping.ui.theme.Blue
import com.google.accompanist.insets.LocalWindowInsets


@Composable
fun ButtonScanner(
    onClick: () -> Unit,
    isNavBarVisible: Boolean
) {


    val context = LocalContext.current
    val paddingBottom = if (isNavBarVisible) {
        val res = context.resources
        val resId = res.getIdentifier(
            "navigation_bar_height",
            "dimen",
            "android"
        )
        res.getDimensionPixelSize(resId) / 2
    } else 16


    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = paddingBottom.dp)
    ) {

        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier
                .width(64.dp)
                .height(64.dp),
            backgroundColor = Blue,
            shape = RoundedCornerShape(25)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_scanner_icon),
                contentDescription = "scanner_button",
                tint = Color.White
            )
        }
    }
}

