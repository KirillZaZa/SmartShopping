package com.conlage.smartshopping.view.components.main.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.conlage.smartshopping.R
import com.conlage.smartshopping.model.data.local.db.entity.Product
import com.conlage.smartshopping.ui.theme.Blue
import com.conlage.smartshopping.view.components.main.list.search.SearchList

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
    searchList: List<Product>,
    onQueryChange: (String) -> Unit,
    onCloseClick: () -> Unit,
    onProductClick: () -> Unit,
    incClick: () -> Unit,
    decClick: () -> Unit
) {

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current


    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(top = 48.dp)
            .background(Color.White, shape = RoundedCornerShape(20)),
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
            CloseSearchButton(focusManager, onCloseClick)
        }




        if (isLoading){
            Spacer(modifier = Modifier.height(24.dp))

            CircularProgressIndicator(color = Blue)

            Spacer(modifier = Modifier.height(24.dp))


        }
        else {
            if(!searchList.isNullOrEmpty()){
                Spacer(modifier = Modifier.height(24.dp))

                SearchList(
                    searchList = searchList,
                    onProductClick = onProductClick,
                    incClick = incClick,
                    decClick = decClick
                )
                Spacer(modifier = Modifier.height(24.dp))

            }
        }



    }
}

@Composable
fun CloseSearchButton(focusManager: FocusManager, onCloseClick: () -> Unit) {

    IconButton(
        onClick = {
            onCloseClick()
            focusManager.clearFocus()
        },
        modifier = Modifier
            .padding(end = 20.dp)
            .size(32.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_close_icon),
            contentDescription = null,
            tint = Color.LightGray
        )
    }
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
            Text(text = "Найти продукт", color = Color.LightGray)
        }

    )
}