package com.conlage.smartshopping.view.screen

import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.conlage.smartshopping.model.data.local.ProductDetails
import com.conlage.smartshopping.model.data.network.dto.NetworkProduct
import com.conlage.smartshopping.ui.theme.BackgroundColor
import com.conlage.smartshopping.view.components.productitem.fab.FabManageProduct
import com.conlage.smartshopping.R
import com.conlage.smartshopping.ui.theme.DarkGray
import com.conlage.smartshopping.view.components.productitem.about.AboutProduct
import com.conlage.smartshopping.view.components.productitem.advantages.AdvantagesProduct
import com.conlage.smartshopping.view.components.productitem.advantages.DisadvantagesProduct
import com.conlage.smartshopping.view.components.productitem.barcode.Barcode
import com.conlage.smartshopping.view.components.productitem.description.DescriptionProduct
import com.conlage.smartshopping.view.components.productitem.dialog.RateDialog
import com.conlage.smartshopping.view.components.productitem.info.InformationProduct


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
        countryOfManufacture = "Россия",
        yearOfResearch = "2020",
        manufacturer = "АО 'БЕДГОРОДСКИЙ МОЛОЧНЫЙ КОМБИНАТ'",
        producer = "АО «Белгородский молочный комбинат»",
        composition = "Цельное молоко",
        barcode = "4601662002218",
        weight = "1л"
    ),
    disadvantages = listOf(),
    id = 72,
    image = "https://rskrf.ru/upload/iblock/d9b/d9b545adda54dfa65779a9bbb57b7dda.jpg",
    price = "63.1 p /л",
    rate = 5.0,
    rate_details =
    mapOf(
        "Безопасность " to 5,
        "Фальсификация" to 5,
        "Антибиотики и токсичные элементы" to 5,
        "Микробиологическая безопасность" to 5,
        "Содержание потенциально опасных веществ" to 5,
        "Органолептические и физико-химические показатели" to 5
    ),
    research_document = "https://rskrf.ru/upload/iblock/e6b/e6bce18b1971e19c2d1f4073ec7dc68d.pdf",
    title = "Молоко Parmalat",
    barcodeImg = null,
    bitmap = null
)


@Composable
fun TextProductHeader(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
                contentDescription = "back_button"
            )
        }

        Spacer(modifier = Modifier.weight(0.5f))
        Text(
            text = title,
            maxLines = 2,
            fontSize = 20.sp,
            color = DarkGray,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.weight(1f))

    }
}


//viewmodel
//navController
@Composable
@Preview
fun ProductScreen(
    navController: NavController? = null
) {

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val bitmap = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.ic_product_standin_icon
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BackgroundColor)
            .padding(horizontal = 20.dp)
            .padding(top = 36.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .scrollable(scrollState, orientation = Orientation.Vertical),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            TextProductHeader(title = productDetails.title) {
                //onclick back
            }

            Spacer(modifier = Modifier.height(32.dp))

            AboutProduct(
                bitmap = bitmap,
                rate = productDetails.rate,
                price = productDetails.price,
                onAboutRateClick = { /*vm handle isRateOpen = !isRateOpen*/ },
                onResearchingClick = {}
            )

            Spacer(modifier = Modifier.height(32.dp))

            DescriptionProduct(
                description = productDetails.description,
                //vm.isReadMore
                isReadMore = false,
                onClickReadMore = {
                    // vm.handleClickReadMore
                }
            )

            Spacer(modifier = Modifier.height(24.dp))


//            Advantages

            Row(horizontalArrangement = Arrangement.SpaceBetween) {

                //passing the advantages list
                AdvantagesProduct(advantagesList = productDetails.advantages)

                Spacer(modifier = Modifier.weight(1f))

                //passing the disadvantages list
                DisadvantagesProduct(disadvantagesList = productDetails.disadvantages)
            }

            //Information

            Spacer(modifier = Modifier.height(24.dp))

            InformationProduct(details = productDetails.details)

            Spacer(modifier = Modifier.height(20.dp))

            //barcode
            Barcode(bitmapBarcode = productDetails.barcodeImg)


            //rate dialog if isRateOpen = true
            if (false) {
                RateDialog(rateDetails = productDetails.rate_details) {
                    //vm isRate = false
                }
            }


        }
        FabManageProduct(onClick = { /*TODO*/ }, isAdded = false)
    }

}