package com.conlage.smartshopping.model.data.media

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.conlage.smartshopping.model.data.media.resultwrapper.LoadResult
import kotlinx.coroutines.CancellationException
import javax.inject.Inject

class ImageDownloaderImpl @Inject constructor(
    private val context: Context
): ImageDownloader {


    override suspend fun downloadImageFromNetwork(url: String, callback: (LoadResult) -> Unit) {
        try{
            val loader = ImageLoader(context.applicationContext)

            val request = ImageRequest.Builder(context.applicationContext)
                .data(url)
                .allowHardware(false)
                .build()

            val result = (loader.execute(request) as SuccessResult).drawable

            val bitmap = (result as BitmapDrawable).bitmap

            callback(LoadResult.Success(bitmap))
        }catch (e : CancellationException){
            callback(LoadResult.Failure(e))
        }
    }

    override suspend fun saveImageToInternalStorage(
        bitmap: Bitmap,
        path: String,
        callback: (LoadResult) -> Unit
    ) {

    }

    override suspend fun deleteImageFromInternalStorage(
        path: String,
        callback: (LoadResult) -> Unit
    ) {

    }


}