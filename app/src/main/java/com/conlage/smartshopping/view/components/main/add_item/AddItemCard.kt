package com.conlage.smartshopping.view.components.main.add_item

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.conlage.smartshopping.ui.theme.Blue
import com.conlage.smartshopping.ui.theme.DividerColor
import com.conlage.smartshopping.ui.theme.Standin
import com.conlage.smartshopping.view.components.extension.clearFocusOnKeyboardDismiss


@Composable
fun AddItemCard(
    onValueChange: (String) -> Unit,
    value: String,
    focusRequester: FocusRequester,
    onDone: KeyboardActionScope.() -> Unit,
    isFocused: (Boolean) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(30))
            .zIndex(11f),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Checkbox(
            enabled = false,
            checked = false,
            onCheckedChange = {},
            modifier = Modifier.padding(start = 4.dp),
            colors = CheckboxDefaults.colors(
                disabledColor = Standin
            )
        )

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            maxLines = 1,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent)
                .offset(x = (-16).dp)
                .focusRequester(focusRequester)
                .onFocusChanged {
                    Log.e("Focus", "AddItemCard: ${it.hasFocus}, ${it.isCaptured}", )
                    isFocused(it.isFocused)
                }
                .clearFocusOnKeyboardDismiss(),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = Blue,
                disabledBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            textStyle = TextStyle(
                color = Color.DarkGray,
                fontWeight = FontWeight.Normal
            ),
            placeholder = {
                Text(
                    text = "Добавить товар",
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,

                )
            },
            keyboardActions = KeyboardActions(
                onDone = onDone
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )


        )
    }


}