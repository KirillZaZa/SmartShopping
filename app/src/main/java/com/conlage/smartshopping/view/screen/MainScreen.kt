package com.conlage.smartshopping.view.screen

import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.conlage.smartshopping.ui.theme.BackgroundColor
import com.conlage.smartshopping.ui.theme.DarkGray
import com.conlage.smartshopping.ui.theme.SmartShoppingTheme
import com.conlage.smartshopping.view.component.AddListItemBtn
import com.conlage.smartshopping.view.component.EmptyListWarning
import com.conlage.smartshopping.view.component.ListItem
import com.conlage.smartshopping.viewModel.MainViewModel

@Preview
@Composable
fun MainScreen(vm: MainViewModel = MainViewModel()) {
    val btnState by remember { mutableStateOf(1)}
    var transition = updateTransition(targetState = btnState, label = "")
    
    SmartShoppingTheme {
        Surface(color = BackgroundColor) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "Список покупок",
                    color = DarkGray,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 32.dp)

                )
                if (vm.checkListItems.isEmpty()){
                    Spacer(modifier = Modifier.weight(1.0f))
                    EmptyListWarning()
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.verticalScroll(rememberScrollState())
                    ) {
                        items(vm.checkListItems) {
                            ListItem()
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1.0f))
                AddListItemBtn {
                    vm.onPlusBtnTap()
                }
            }
        }
    }
}
