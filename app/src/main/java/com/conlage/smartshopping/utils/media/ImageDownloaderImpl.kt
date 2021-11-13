package com.conlage.smartshopping.utils.media

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.conlage.smartshopping.utils.media.resultwrapper.LoadResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.*
import java.lang.Exception
import javax.inject.Inject

class ImageDownloaderImpl @Inject constructor(
    private val context: Context,
) : ImageDownloader {

    @Deprecated("Migrated to CoilImage")
    override suspend fun downloadImageFromNetwork(
        url: String,
    ):LoadResult.Success<Bitmap> {

        return try {
            val response = Glide
                .with(context)
                .asBitmap()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .submit(124, 124)
                .get()
            LoadResult.Success(response)

        }catch (e : Exception){
            e.printStackTrace()
            LoadResult.Success(null)
        }




    }

    override suspend fun downloadImageForPage(url: String): LoadResult<Bitmap> {
        return try {
            val response = Glide
                .with(context)
                .asBitmap()
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .submit()
                .get()
            LoadResult.Success(response)

        } catch (e: Exception) {
            e.printStackTrace()
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

fun logError(e: Throwable) {
    Log.e(ImageDownloaderImpl::class.java.simpleName, "logError: ${e.localizedMessage}")
}


