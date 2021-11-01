package com.conlage.smartshopping.utils.media

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.conlage.smartshopping.R
import com.conlage.smartshopping.utils.media.resultwrapper.LoadResult
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*
import java.lang.Exception
import javax.inject.Inject

class ImageDownloaderImpl @Inject constructor(
    private val context: Context
) : ImageDownloader {


    override suspend fun downloadImageFromNetwork(url: String): LoadResult<Bitmap> {
        return try {
            val loader = ImageLoader(context.applicationContext)

            val request = ImageRequest.Builder(context.applicationContext)
                .data(url)
                .allowHardware(true)
                .size(150,150)
                .error(R.drawable.ic_product_standin_icon)
                .diskCachePolicy(CachePolicy.ENABLED)
                .build()

            val imgResult = loader.execute(request)
            val bitmap = imgResult.drawable!!.toBitmap()



            LoadResult.Success(bitmap)
        } catch (e: CancellationException) {
            logError(e)
            LoadResult.Failure()
        }
    }

    override fun saveImageToInternalStorage(
        bitmap: Bitmap,
        image: String
    ) {
        try{
            context.openFileOutput("$image.jpg", Context.MODE_PRIVATE).use {stream->
                if(!bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)){
                    throw IOException()
                }
            }
        }catch (e: Exception){
            logError(e)
        }
    }

    override suspend fun loadImageFromInternalStorage(image: String): LoadResult<Bitmap> {
        var loadResult: LoadResult<Bitmap>? = null
        try{
            withContext(Dispatchers.IO){
                val files = context.filesDir.listFiles()

                files?.filter { file -> file.canRead() && file.isFile && file.name.equals("$image.jpg")
                }?.map {
                    val imageBytes = it.readBytes()
                    loadResult = LoadResult.Success(BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size))
                } ?: { loadResult = LoadResult.Failure() }
            }
        }catch (e: Throwable){

        }

        return loadResult!!
    }

    override fun deleteImageFromInternalStorage(image: String) {
        try {
            context.deleteFile("$image.jpg")
        }catch (e: IOException){
            logError(e)
        }
    }

}

fun ImageDownloaderImpl.logError(e: Throwable){
    Log.e(ImageDownloaderImpl::class.java.simpleName, "logError: ${e.localizedMessage}", )
}


