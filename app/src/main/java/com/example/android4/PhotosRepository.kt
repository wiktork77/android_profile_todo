package com.example.android4

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File

class PhotosRepository {
    lateinit var uri: Uri


    fun getMode(ctx: Context): String {
        return ctx.getSharedPreferences("userData", Context.MODE_PRIVATE).getString("mode", " ") as String
    }

    fun getDataList(ctx: Context): MutableList<PhotoItem> {
        return when (getMode(ctx)) {
            "app" -> getAppList(ctx)
            "shared" -> getSharedList(ctx)
            else -> mutableListOf()
        }
    }

    private fun getSharedList(ctx: Context): MutableList<PhotoItem> {
        val images: MutableList<PhotoItem> = mutableListOf()

        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val contentResolver: ContentResolver = ctx.contentResolver
        val cursor = contentResolver.query(uri, null, null, null, null)
        if (cursor == null) {
            return images
        } else if (!cursor.moveToFirst()) {
            return images
        } else {
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            do {
                val thisId = cursor.getLong(idColumn)
                val thisName = cursor.getString(nameColumn)
                val thisContentUri = ContentUris.withAppendedId(uri, thisId)
                images.add(PhotoItem(thisName, thisContentUri))
            } while (cursor.moveToNext())
        }
        images.reverse()
        return images
    }

    private fun getAppList(ctx: Context): MutableList<PhotoItem> {
        val dir: File? = ctx.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        dir?.listFiles()

        val outputList: MutableList<PhotoItem> = mutableListOf()

        if (dir?.isDirectory() == true) {
            var fileList = dir.listFiles()
            if (fileList != null) {
                for (value in fileList) {
                    var fileName = value.name
                    if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ||
                        fileName.endsWith(".png") || fileName.endsWith(".gif")) {
                        val tmpUri = FileProvider.getUriForFile(ctx, "${BuildConfig.APPLICATION_ID}.provider", value)
                        outputList.add(PhotoItem(fileName, tmpUri))
                    }
                }
            }
        }
        outputList.reverse()
        return outputList
    }

    companion object {
        private var INSTANCE: PhotosRepository? = null
        fun getInstance(): PhotosRepository {
            if (INSTANCE == null) {
                INSTANCE = PhotosRepository()
            }
            return INSTANCE as PhotosRepository
        }
    }
}