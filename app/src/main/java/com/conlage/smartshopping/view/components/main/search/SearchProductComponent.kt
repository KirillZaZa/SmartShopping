package com.conlage.smartshopping.view.components.main.search

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.Guideline
import com.conlage.smartshopping.R
import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.ui.theme.Blue
import com.conlage.smartshopping.ui.theme.DarkGray
import com.conlage.smartshopping.ui.theme.LightGray
import com.conlage.smartshopping.view.components.main.list.search.SearchList
import com.conlage.smartshopping.view.components.main.warning.EmptySearchWarning

/**
 *
 *@param onChangedText: (String) -> Unit
 *
 *
 */
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
    incClick: (Int) -> Unit,
    decClick: (Int) -> Unit,
    onAddTextClick: () -> Unit,
    focusRequester: FocusRequester,
    focusManager: FocusManager
) {


    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(top = 48.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(20.dp), clip = true)
            .background(Color.White, shape = RoundedCornerShape(20.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //search edit
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            SearchField(searchQuery, focusRequester, onQueryChange)
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

            Spacer(modifier = Modifier.height(36.dp))
            EmptySearchWarning()
            Spacer(modifier = Modifier.height(36.dp))
            AddTextProductButton(onAddTextClick)
            Spacer(modifier = Modifier.height(36.dp))

        } else if (!searchList.isNullOrEmpty()) {

            SearchList(
                searchList = searchList,
                onProductClick = onProductClick,
                incClick = incClick,
                decClick = decClick
            )


        }
    }


}

@Composable
fun AddTextProductButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 24.dp)
            .background(color = Blue, shape = RoundedCornerShape(35)),
        contentAlignment = Alignment.Center
    ) {
        TextButton(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Добавить в список",
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
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

@Composable
fun SearchField(
    searchQuery: String?,
    focusRequester: FocusRequester,
    onQueryChange: (String) -> Unit,
) {

    OutlinedTextField(
        value = searchQuery ?: "",
        onValueChange = onQueryChange,
        maxLines = 1,
        modifier = Modifier
            .background(color = Color.Transparent)
            .padding(start = 20.dp)
            .focusRequester(focusRequester),
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
        }


    )
}