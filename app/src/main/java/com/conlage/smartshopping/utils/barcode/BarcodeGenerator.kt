package com.conlage.smartshopping.utils.barcode

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.oned.Code128Writer
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class BarcodeGenerator @Inject constructor() {

    suspend fun generateBarcodeBitmap(code: String): Bitmap? {
        try {
            return withContext(Dispatchers.Default) {
                val hintMap = Hashtable<EncodeHintType, ErrorCorrectionLevel>()
                hintMap[EncodeHintType.ERROR_CORRECTION] = ErrorCorrectionLevel.L

                val codeWriter = Code128Writer()

                val byteMatrix = codeWriter.encode(code, BarcodeFormat.CODE_128, 600, 128, hintMap)

                val width = byteMatrix.width
                val height = byteMatrix.height

                val barcodeBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                for (i in 0 until width) {
                    for (j in 0 until height) {
                        val color = if (byteMatrix.get(i, j)) Color.BLACK
                        else Color.WHITE
                        barcodeBitmap.setPixel(i, j, color)
                    }
                }
                barcodeBitmap
            }
        } catch (e: CancellationException) {
            e.printStackTrace()
        }
        return null
    }

}