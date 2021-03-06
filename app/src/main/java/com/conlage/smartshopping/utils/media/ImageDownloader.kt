package com.conlage.smartshopping.utils.media

import android.graphics.Bitmap
import com.conlage.smartshopping.utils.media.resultwrapper.LoadResult


interface ImageDownloader {

    @Deprecated("Migrated to CoilImage")
    suspend fun downloadImageFromNetwork(
        url: String,

    ):LoadResult.Success<Bitmap>

    fun saveImageToInternalStorage(bitmap: Bitmap, image: String)

    suspend fun loadImageFromInternalStorage(image: String): LoadResult<Bitmap>

    fun deleteImageFromInternalStorage(image: String)

    suspend fun downloadImageForPage(url: String): LoadResult<Bitmap>

}