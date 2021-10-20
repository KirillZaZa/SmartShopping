package com.conlage.smartshopping.view.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.conlage.smartshopping.ui.theme.LightGray
import com.conlage.smartshopping.ui.theme.Purple
import com.conlage.smartshopping.ui.theme.SmartShoppingTheme
import com.conlage.smartshopping.viewmodel.impl.MainViewModelImpl


@Composable
fun ListItem(
   viewModelImpl: MainViewModelImpl
) {
    SmartShoppingTheme {
        Card(
            elevation = 0.dp,
            shape = RoundedCornerShape(25)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(color = Color.White)
                    .fillMaxWidth()
                    .padding(all = 8.dp)
            ) {
                //Checkbox
                Checkbox(
                    checked = true,
                    onCheckedChange = {}
//                    onCheckedChange = viewModel.onChecked()
                )
                //Title
                Text(
                    text = "Творог Лента 5%",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                //Count
                Button(
                    onClick = {

                    }, colors = ButtonDefaults.buttonColors(
                        backgroundColor = LightGray,
                        contentColor = Purple
                    ),
                    modifier = Modifier
                        .width(56.dp)
                        .height(32.dp),
                    shape = RoundedCornerShape(25),
                    elevation = null
                ) {
                    Text(
                        text = "4",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Purple
                    )
                }
            }
        }
    }
}