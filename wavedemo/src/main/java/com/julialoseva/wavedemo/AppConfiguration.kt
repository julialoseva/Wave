package com.julialoseva.wavedemo

import android.content.Context
import android.os.Environment

object AppConfiguration {

    fun getRecordFilePath(
        context: Context,
        fileName: String
    ): String {
        val environmentDirectory = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_MUSIC
        ).absolutePath
        val appDirectoryName = context.resources.getString(
            R.string.app_name
        )
        return "$environmentDirectory/$appDirectoryName/${fileName}"
    }
}