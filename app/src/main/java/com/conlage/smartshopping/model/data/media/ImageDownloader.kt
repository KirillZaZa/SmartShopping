package com.conlage.smartshopping.model.data.media

import android.graphics.Bitmap
import com.conlage.smartshopping.model.data.media.resultwrapper.LoadResult


interface ImageDownloader {

    suspend fun downloadImageFromNetwork(url: String, callback: (LoadResult) -> Unit)

    suspend fun saveImageToInternalStorage(bitmap: Bitmap, path: String, callback: (LoadResult) -> Unit)

    suspend fun deleteImageFromInternalStorage(path: String, callback: (LoadResult) -> Unit)

}