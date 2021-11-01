package com.conlage.smartshopping.view.screen

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.conlage.smartshopping.model.data.local.ProductDetails
import com.conlage.smartshopping.model.data.network.dto.NetworkProduct
import com.conlage.smartshopping.ui.theme.BackgroundColor
import com.conlage.smartshopping.view.components.product.fab.FabManageProduct
import com.conlage.smartshopping.viewmodel.impl.ProductViewModelImpl


val productDetails = ProductDetails(
    advantages = listOf(
        "Пастеризованное",
        "Не содержит растительных жиров",
        "Без добавления сухого молока",
        "Высокая органолептическая оценка",
        "Безопасно",
        "Достаточное количество белков и жиров",
        "Молоко хорошего качества"
    ),
    barcode = "4601662002218",
    description = "Молоко питьевое цельное пастеризованное, " +
            "с массовой долей жира 3,4%, под торговой маркой Parmalat " +
            "произведено АО «Белгородский молочный комбинат» в России.\nПо результатам " +
            "лабораторных испытаний данный товар признан высококачественным, так как он " +
            "соответствовал не только обязательным требованиям законодательства, но и опережающему " +
            "стандарту Роскачества. \nЭто молоко безопасно. Антибиотики в его составе отсутствуют. " +
            "Кроме того, в нем не было обнаружено в количествах, опасных для здоровья, нитрофуранов, " +
            "а также микотоксинов, которые могут попадать в состав молока из кормов для животных. " +
            "Микробиологические показатели соответствуют установленным требованиям. \nЭто молоко свежее. " +
            "Пищевая ценность продукта соответствует маркировке. Массовая доля сухого обезжиренного молочного " +
            "остатка соответствует установленным нормам. Кроме того, молоко было пастеризовано с соблюдением " +
            "всех технологий. Результаты исследования состава стеринов показали, что в молоке нет растительных жиров. " +
            "Сухое молоко в составе также отсутствует. \nВнешний вид молока – его цвет, консистенция, " +
            "а также вкус и запах соответствуют данной товарной категории. Фактическая масса нетто соответствует " +
            "указанной в маркировке.\nПосле проведения оценки производства, в ходе которой был подтвержден уровень " +
            "его локализации, данному товару присвоен российский Знак качества.",
    details = NetworkProduct.Response.Details(
        countryOfManufacture = "2020",
        yearOfResearch = "2020",
        manufacturer = "АО 'БЕДГОРОДСКИЙ МОЛОЧНЫЙ КОМБИНАТ'",
        producer = "АО «Белгородский молочный комбинат»",
        composition = "цельное молоко",
        barcode = "4601662002218",
        weight = "1л"
    ),
    disadvantages = listOf(),
    id =  72,
    image = "https://rskrf.ru/upload/iblock/d9b/d9b545adda54dfa65779a9bbb57b7dda.jpg",
    price =  "63.1 p /л",
    rate = 5.0,
    rate_details = NetworkProduct.Response.RateDetails(5.0,5.0,5),
    research_document = "https://rskrf.ru/upload/iblock/e6b/e6bce18b1971e19c2d1f4073ec7dc68d.pdf",
    title = "Молоко Parmalat",
    barcodeImg = null,
    bitmap = null
)
//viewmodel

//navController
@Composable
@Preview
fun ProductScreen(
    navController: NavController? = null
) {

    val scrollState = rememberScrollState()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = BackgroundColor)
        .padding(horizontal = 20.dp)
        .padding(top = 36.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(modifier = Modifier
            .fillMaxSize()) {

        }
        FabManageProduct(onClick = { /*TODO*/ }, isAdded = false)
    }

}