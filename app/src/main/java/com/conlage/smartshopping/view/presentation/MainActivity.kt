package com.conlage.smartshopping.view.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.conlage.smartshopping.view.screen.MainScreen
import com.conlage.smartshopping.viewmodel.impl.MainViewModelImpl


class MainActivity : ComponentActivity() {
    private val viewModelImpl: MainViewModelImpl by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(viewModelImpl)
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
//    MainScreen()
}