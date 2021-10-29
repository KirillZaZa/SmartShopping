package com.conlage.smartshopping.view.components.main.floating_button

import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.conlage.smartshopping.R
import com.conlage.smartshopping.ui.theme.Blue


@Composable
fun ButtonNewProduct(
    state: FabStateEnum,
    onFabItemClick: (FabItem) -> Unit,
    onClick: () -> Unit,
) {

    val transition: Transition<FabStateEnum> = updateTransition(targetState = state, label = null)
    val rotation: Float by transition.animateFloat { state ->
        if (state == FabStateEnum.EXPANDED) 45f else 0f
    }


    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        if(transition.currentState == FabStateEnum.EXPANDED){
            CameraFab {
                onFabItemClick(FabItem.CameraFabItem)
            }
            Spacer(modifier = Modifier.height(16.dp))

            AddFab {
                onFabItemClick(FabItem.AddFabItem)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier
                .width(64.dp)
                .height(64.dp),
            backgroundColor = Blue,
            shape = RoundedCornerShape(25)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_add_24),
                contentDescription = null,
                modifier = Modifier.rotate(rotation),
                tint = Color.White
            )
        }
    }
}


@Composable
fun CameraFab(onFabItemClick: () -> Unit) {
    FloatingActionButton(
        onClick = onFabItemClick,
        modifier = Modifier
            .height(48.dp)
            .width(48.dp),
        backgroundColor = Color.Blue,
        shape = RoundedCornerShape(20)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_scanner_icon),
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Composable
fun AddFab(onFabItemClick: () -> Unit) {
    FloatingActionButton(
        onClick = onFabItemClick,
        modifier = Modifier
            .height(48.dp)
            .width(48.dp),
        backgroundColor = Color.Blue,
        shape = RoundedCornerShape(20)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_add_24),
            contentDescription = null,
            tint = Color.White
        )
    }
}