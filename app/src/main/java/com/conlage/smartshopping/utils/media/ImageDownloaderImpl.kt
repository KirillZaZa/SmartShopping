package com.conlage.smartshopping.utils.media

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.conlage.smartshopping.R
import com.conlage.smartshopping.utils.media.resultwrapper.LoadResult
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*
import java.lang.Exception
import javax.inject.Inject

class ImageDownloaderImpl @Inject constructor(
    private val context: Context,
) : ImageDownloader {


    override suspend fun downloadImageFromNetwork(
        url: String,
    ):LoadResult.Success<Bitmap> {

        return try {
            val response = Glide
                .with(context)
                .asBitmap()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.overrideOf(124, 124))
                .submit()
                .get()
            LoadResult.Success(response)

        }catch (e : Exception){
            e.printStackTrace()
            LoadResult.Success(null)
        }




    }

    override suspend fun downloadImageForPage(url: String): LoadResult<Bitmap> {
        return try {
            val loader = ImageLoader(context.applicationContext)

            val request = ImageRequest.Builder(context.applicationContext)
                .data(url)
                .allowHardware(true)
                .error(R.drawable.ic_product_standin_icon)
                .diskCachePolicy(CachePolicy.ENABLED)
                .build()

            val imgResult = when (val result = loader.execute(request)) {
                is SuccessResult -> result.drawable
                else -> null
            }
            val bitmap = imgResult?.toBitmap()



            LoadResult.Success(bitmap)
        } catch (e: CancellationException) {
            logError(e)
            LoadResult.Failure()
        }
    }


    @Deprecated("Useless in v1.0")
    override fun saveImageToInternalStorage(
        bitmap: Bitmap,
        image: String
    ) {
        try {
            context.openFileOutput("$image.jpg", Context.MODE_PRIVATE).use { stream ->
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)) {
                    throw IOException()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    @Deprecated("Useless in v1.0")
    override suspend fun loadImageFromInternalStorage(image: String): LoadResult<Bitmap> {
        return try {
            withContext(Dispatchers.IO) {
                val files = context.filesDir.listFiles()

                val bitmap = files?.filter { file ->

                    file.canRead() && file.isFile && file.name.equals("$image.jpg")

                }?.map {
                    val imageBytes = it.readBytes()
                    BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                }?.get(0)

                if (bitmap == null) LoadResult.Failure()
                else LoadResult.Success(bitmap)

            }

        } catch (e: Throwable) {
            e.printStackTrace()
            LoadResult.Failure()
        }
    }

    @Deprecated("Useless in v1.0")
    override fun deleteImageFromInternalStorage(image: String) {
        try {
            context.deleteFile("$image.jpg")
        } catch (e: IOException) {
            logError(e)
        }
    }

}

fun ImageDownloaderImpl.logError(e: Throwable) {
    Log.e(ImageDownloaderImpl::class.java.simpleName, "logError: ${e.localizedMessage}")
}


