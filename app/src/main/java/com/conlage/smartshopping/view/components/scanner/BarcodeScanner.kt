package com.conlage.smartshopping.view.components.scanner

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import com.conlage.smartshopping.R
import com.conlage.smartshopping.ui.theme.Blue
import com.conlage.smartshopping.ui.theme.Standin
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.CompoundBarcodeView


@Composable
fun BarcodeScanner(
    activity: Activity,
    onBarcodeResult: (String) -> Unit
) {
    val context = LocalContext.current
    val scanFlag = remember { mutableStateOf(false) }

    val compoundBarcodeView = remember {
        CompoundBarcodeView(context).apply {
            val capture = CaptureManager(activity, this)
            capture.initializeFromIntent(activity.intent, null)
            this.setStatusText("")

            Log.e("Barcode", "resumed")


            capture.decode()

            this.resume()

            this.decodeContinuous { result ->
                if (scanFlag.value) {
                    return@decodeContinuous
                }

                scanFlag.value = true

                result.text?.let { barCodeQr ->

                    onBarcodeResult(barCodeQr)

                    this.pause()

                    scanFlag.value = true
                }
            }

        }
    }


    AndroidView(
        modifier = Modifier
            .size(width = 300.dp, height = 300.dp)
            .clip(RoundedCornerShape(20.dp)),
        factory = { compoundBarcodeView }
    )

}


@ExperimentalComposeUiApi
@Composable
fun DialogScanner(
    onDismissRequest: () -> Unit,
    onBarcodeResult: (String) -> Unit,
    activity: Activity
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = true
        )
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            BarcodeScanner(
                activity = activity,
                onBarcodeResult = onBarcodeResult
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Сканируйте штрих-код товара",
                color = Standin,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )


        }

    }
}