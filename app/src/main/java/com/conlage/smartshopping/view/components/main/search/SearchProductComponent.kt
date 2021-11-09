package com.conlage.smartshopping.view.components.main.search

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeOptions
import androidx.compose.ui.unit.dp
import com.conlage.smartshopping.R
import com.conlage.smartshopping.model.data.local.Product
import com.conlage.smartshopping.ui.theme.Blue
import com.conlage.smartshopping.view.components.extension.clearFocusOnKeyboardDismiss
import com.conlage.smartshopping.view.components.main.search.list.SearchList
import com.conlage.smartshopping.view.components.main.warning.EmptySearchWarning
import com.google.accompanist.insets.LocalWindowInsets

/**
 *
 *@param onChangedText: (String) -> Unit
 *
 *
 */
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun SearchProductComp(
    searchQuery: String,
    isLoading: Boolean,
    isSearchError: Boolean,
    searchList: List<Product>,
    onQueryChange: (String) -> Unit,
    onCloseClick: () -> Unit,
    onProductClick: (Int) -> Unit,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    onFocusChanged: () -> Unit
) {

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .sizeIn(maxHeight = 500.dp)
            .fillMaxWidth()
            .padding(top = 48.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(20.dp), clip = true)
            .zIndex(10f)
            .background(Color.White, shape = RoundedCornerShape(20.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //search edit
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            SearchField(
                searchQuery,
                focusRequester,
                focusManager,
                onQueryChange,
                onFocusChanged = onFocusChanged
            )
            Spacer(modifier = Modifier.weight(1f))

            if (searchQuery.isNotBlank()) {
                CloseSearchButton(focusManager, onCloseClick)
            } else SearchIcon()
        }




        if (isLoading) {
            Spacer(modifier = Modifier.height(24.dp))

            CircularProgressIndicator(color = Blue)

            Spacer(modifier = Modifier.height(24.dp))


        } else if (isSearchError) {

            Spacer(modifier = Modifier.height(24.dp))
            EmptySearchWarning()
            Spacer(modifier = Modifier.height(40.dp))

        } else if (!searchList.isNullOrEmpty()) {

            SearchList(
                searchList = searchList,
                onProductClick = onProductClick,
            )


        }
    }


}


@Composable
fun CloseSearchButton(
    focusManager: FocusManager,
    onCloseClick: () -> Unit,
) {

    IconButton(
        onClick = {
            onCloseClick()
            focusManager.clearFocus()
        },
        modifier = Modifier
            .padding(end = 20.dp)
            .size(28.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_close_icon),
            contentDescription = null,
            tint = Color.LightGray
        )
    }
}

@Composable
fun SearchIcon(
) {

    Icon(
        painter = painterResource(id = R.drawable.ic_search_icon),
        contentDescription = null,
        tint = Color.LightGray,
        modifier = Modifier
            .padding(end = 20.dp)
            .size(28.dp)
    )
}

@ExperimentalComposeUiApi
@Composable
fun SearchField(
    searchQuery: String?,
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    onQueryChange: (String) -> Unit,
    onFocusChanged: () -> Unit
) {


    OutlinedTextField(
        value = searchQuery ?: "",
        onValueChange = onQueryChange,
        maxLines = 1,
        modifier = Modifier
            .sizeIn(maxWidth = 304.dp)
            .background(color = Color.Transparent)
            .padding(start = 20.dp)
            .focusRequester(focusRequester)
            .onFocusChanged {
                if (!it.isFocused) {
                    onFocusChanged()
                }
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
            Text(text = "Название товара", color = Color.LightGray)
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),

        )
}