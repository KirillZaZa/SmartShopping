package com.conlage.smartshopping.viewModel

import android.graphics.Color
import androidx.compose.material.CheckboxDefaults
import androidx.lifecycle.ViewModel
import com.conlage.smartshopping.model.Product

class MainViewModel : ViewModel() {

    //Массив со всеми элементами чеклиста
    var checkListItems: MutableList<Product> = ArrayList()

    //Состояние экрана
    // |-Обычное
    // |-Добавление нового элемента

    //При нажатии на кнопку "+"
    fun onPlusBtnTap() {
        //Опустить список вниз и добавить элемент наверх

        //Добавить empty элемент с placeholder
    }

    //При нажатии на чекбокс ЛЮБОГО элемента списка
//    fun onChecked(isChecked: Boolean) {
//
//    }

}